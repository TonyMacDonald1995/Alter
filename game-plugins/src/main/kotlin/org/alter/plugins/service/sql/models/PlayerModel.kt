package org.alter.plugins.service.sql.models

import org.jetbrains.exposed.sql.Table

object PlayerModel : Table("Players") {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val username = varchar("username", 60).uniqueIndex()
    val displayName = varchar("display_name", 60).uniqueIndex().nullable()
    val x = integer("position_x")
    val z = integer("position_z")
    val height = integer("height")
    val privilege = integer("privilege").default(0)
    val runEnergy = double("run_energy").default(100.toDouble())
    val displayMode = integer("display_mode").default(0)
    val hash = varchar("password_hash", 60)
    val xteaKeyOne = integer("xtea_one")
    val xteaKeyTwo = integer("xtea_two")
    val xteaKeyThree = integer("xtea_three")
    val xteaKeyFour = integer("xtea_four")
}