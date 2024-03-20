package org.alter.plugins.service.sql.models

import org.jetbrains.exposed.sql.Table

object TimerModel : Table("Timers") {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val playerId = (integer("player_id") references PlayerModel.id).nullable()
    val currentMs = long("current_ms")
    val identifier = varchar("identifier", 1024)
    val tickOffline = bool("tick_offline").default(false)
    val timeLeft = integer("time_left")
}