package org.alter.plugins.service.sql.models

import org.jetbrains.exposed.sql.Table

object ItemContainerModel : Table("ItemContainer") {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val name = varchar("name", 60)
    val playerId = (integer("player_id") references PlayerModel.id).nullable()
}