package org.alter.plugins.service.sql.models

import org.jetbrains.exposed.sql.Table

object SkillModel : Table("Skills") {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val playerId = (integer("player_id") references PlayerModel.id).nullable()
    val skill = integer("skill_id")
    val xp = double("xp").default(0.0)
    val lvl = integer("lvl").default(1)
}