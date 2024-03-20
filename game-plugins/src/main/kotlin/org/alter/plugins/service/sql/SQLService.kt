package org.alter.plugins.service.sql

import gg.rsmod.net.codec.login.LoginRequest
import gg.rsmod.util.ServerProperties
import io.github.oshai.kotlinlogging.KLogging
import org.alter.game.Server
import org.alter.game.model.PlayerUID
import org.alter.game.model.Tile
import org.alter.game.model.World
import org.alter.game.model.attr.AttributeKey
import org.alter.game.model.container.ItemContainer
import org.alter.game.model.entity.Client
import org.alter.game.model.interf.DisplayMode
import org.alter.game.model.item.Item
import org.alter.game.model.priv.Privilege
import org.alter.game.model.timer.TimerKey
import org.alter.game.service.serializer.PlayerLoadResult
import org.alter.game.service.serializer.PlayerSerializerService
import org.alter.plugins.service.sql.controllers.LoadController
import org.alter.plugins.service.sql.controllers.SaveController
import org.alter.plugins.service.sql.models.*
import org.alter.plugins.service.sql.serializers.SQLSerializer
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class SQLService : PlayerSerializerService() {

    override fun initSerializer(server: Server, world: World, serviceProperties: ServerProperties) {
        val driver = serviceProperties.getOrDefault("driver", "com.mysql.jdbc.Driver")
        val port = serviceProperties.getOrDefault("port", 3306)
        val host = serviceProperties.getOrDefault("host", "127.0.0.1")
        val user = serviceProperties.getOrDefault("user", "alter")
        val pswd = serviceProperties.getOrDefault("password", "alter")
        val dbname = serviceProperties.getOrDefault("dbname", "alter")

        val driverHost = when(driver) {
            "com.mysql.cj.jdbc.Driver" -> "mysql"
            "org.postgresql.Driver" -> "postgresql"
            "oracle.jdbc.OracleDriver" -> "jdbc:oracle:thin:@"
            "com.microsoft.sqlserver.jdbc.SQLServerDriver" -> "sqlserver"
            else -> throw IllegalArgumentException("Invalid JDBC Driver")
        }

        Database.connect("jdbc:$driverHost://$host:$port/$dbname?allowPublicKeyRetrieval=true&useSSL=false", driver = driver, user = user, password = pswd)

        transaction {
            mutableListOf(
                PlayerModel,
                SkillModel,
                ItemContainerModel,
                ItemModel,
                AttributesModel,
                VarpModel,
                ItemAttributeModel,
                TimerModel
            ).forEach { table ->
                if (!table.exists()) {
                    SchemaUtils.create(table)
                    log("${table.tableName} did not exist. Created successfully...")
                }
            }
        }
    }

    override fun loadClientData(client: Client, request: LoginRequest): PlayerLoadResult {
        val serialize: SQLSerializer = LoadController().loadPlayer(client)
            ?: return configureNewAccount(client, request)


        val previousXteas = IntArray(4)

        previousXteas[0] = serialize.player[PlayerModel.xteaKeyOne]
        previousXteas[1] = serialize.player[PlayerModel.xteaKeyTwo]
        previousXteas[2] = serialize.player[PlayerModel.xteaKeyThree]
        previousXteas[3] = serialize.player[PlayerModel.xteaKeyFour]

        if (!request.reconnecting) {
            if (!BCrypt.checkpw(request.password, serialize.player[PlayerModel.hash])) {
                return PlayerLoadResult.INVALID_CREDENTIALS
            }
        } else {
            if (!previousXteas.contentEquals(request.xteaKeys)) {
                return PlayerLoadResult.INVALID_CREDENTIALS
            }
        }

        client.uid = PlayerUID(serialize.player[PlayerModel.id])
        client.passwordHash = serialize.player[PlayerModel.hash]
        client.displayname = serialize.player[PlayerModel.displayName] ?: "Player #${client.uid.value}"
        client.runEnergy = serialize.player[PlayerModel.runEnergy]
        client.privilege = client.world.privileges.get(serialize.player[PlayerModel.privilege]) ?: Privilege.DEFAULT
        client.tile = Tile(serialize.player[PlayerModel.x], serialize.player[PlayerModel.z], serialize.player[PlayerModel.height])
        client.interfaces.displayMode = DisplayMode.values.firstOrNull { it.id == serialize.player[PlayerModel.displayMode] } ?: DisplayMode.FIXED

        serialize.skillModels.forEach { skill ->
            client.getSkills().setXp(skill[SkillModel.skill], skill[SkillModel.xp])
            client.getSkills().setCurrentLevel(skill[SkillModel.skill], skill[SkillModel.lvl])
        }

        serialize.itemContainers.forEach { container ->
            val key = client.world.plugins.containerKeys.firstOrNull { other -> other.name == container.container[ItemContainerModel.name]}
                ?: return@forEach

            val cont = if (client.containers.containsKey(key)) client.containers[key] else {
                client.containers[key] = ItemContainer(client.world.definitions, key)
                client.containers[key]
            } ?: return@forEach

            container.items.forEach { item ->
                val i = Item(item.item[ItemModel.itemId], item.item[ItemModel.amount])
                item.attributes.forEach { attr ->
                    //i.attr[attr[ItemAttributeModel.key]] = attr[ItemAttributeModel.value] TODO
                }
                cont[item.item[ItemModel.index]] = i
            }
        }

        serialize.attributeModels.forEach { attr ->
            client.attr[AttributeKey<Any>(attr[AttributesModel.key])] = attr[AttributesModel.value]
        }

        serialize.timerModels.forEach { timer ->
            var time = timer[TimerModel.timeLeft]
            val tick = timer[TimerModel.tickOffline]
            if (tick) {
                val elapsed = System.currentTimeMillis() - timer[TimerModel.currentMs]
                val ticks = (elapsed / client.world.gameContext.cycleTime).toInt()
                time -= ticks
            }

            client.timers[TimerKey(timer[TimerModel.identifier], tick)] = 0.coerceAtLeast(time)
        }

        serialize.varpModels.forEach { varp ->
            client.varps.setState(varp[VarpModel.varpId], varp[VarpModel.state])
        }

        return PlayerLoadResult.LOAD_ACCOUNT
    }

    private fun configureNewAccount(client: Client, request: LoginRequest): PlayerLoadResult {
        configureNewPlayer(client, request)
        client.uid = SaveController.createPlayer(client, client.world)
        client.displayname = "Player #${client.uid.value}"
        return PlayerLoadResult.NEW_ACCOUNT
    }

    /**
     * Returns true if [displayName] is in use,
     * false otherwise
     */
    fun checkDisplayName(checkedName: String): Boolean {
        var found = false
        transaction {
            val result = PlayerModel.selectAll().where { PlayerModel.displayName.lowerCase() eq checkedName.lowercase() }
            if (!result.empty())
                found = true
        }
        return found
    }

    override fun saveClientData(client: Client): Boolean {
        return SaveController.savePlayer(client)
    }

    private fun log(message: String) {
        logger.info { message }
    }

    companion object: KLogging()

}