package org.alter.plugins.service.sql.controllers

import org.alter.game.model.entity.Client
import org.alter.plugins.service.sql.models.*
import org.alter.plugins.service.sql.serializers.Item
import org.alter.plugins.service.sql.serializers.ItemContainer
import org.alter.plugins.service.sql.serializers.SQLSerializer
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class LoadController : Controller() {
    fun loadPlayer(client: Client): SQLSerializer? {
        var serialize: SQLSerializer? = null

        transaction {
            val player = PlayerModel.selectAll().where {
                PlayerModel.username eq client.loginUsername
            }.firstOrNull() ?: return@transaction

            val containers: MutableList<ItemContainer> = mutableListOf()

            ItemContainerModel.selectAll().where {
                ItemContainerModel.playerId eq player[PlayerModel.id]
            }.forEach { container ->
                val items: MutableList<Item> = mutableListOf()

                ItemModel.selectAll().where {
                    ItemModel.containerId eq container[ItemContainerModel.id]
                }.forEach { item ->
                    val attributes: MutableList<ResultRow> = mutableListOf()

                    ItemAttributeModel.selectAll().where {
                        ItemAttributeModel.itemId eq item[ItemModel.id]
                    }.forEach { itemAttr ->
                        attributes.add(itemAttr)
                    }

                    items.add(Item(item, attributes))
                }

                containers.add(ItemContainer(items, container))
            }

            val attributes: MutableList<ResultRow> = mutableListOf()

            AttributesModel.selectAll().where {
                AttributesModel.playerId eq player[PlayerModel.id]
            }.forEach {
                attributes.add(it)
            }

            val skills: MutableList<ResultRow> = mutableListOf()

            SkillModel.selectAll().where {
                SkillModel.playerId eq player[PlayerModel.id]
            }.forEach {
                skills.add(it)
            }

            val timers: MutableList<ResultRow> = mutableListOf()

            TimerModel.selectAll().where {
                TimerModel.playerId eq player[PlayerModel.id]
            }.forEach {
                timers.add(it)
            }

            val varps: MutableList<ResultRow> = mutableListOf()

            VarpModel.selectAll().where {
                VarpModel.playerId eq player[PlayerModel.id]
            }.forEach {
                varps.add(it)
            }

            serialize = SQLSerializer(player, containers, skills, attributes, timers, varps)
        }

        return serialize
    }
}