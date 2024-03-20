package org.alter.plugins.service.sql.models

import org.jetbrains.exposed.sql.Table

object VarpModel : Table("Varps") {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val playerId = (integer("player_id") references PlayerModel.id).nullable()
    val varpId = integer("varp_id")
    val state = integer("state")
}