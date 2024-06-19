package org.alter.plugins.content.skills.mining

import org.alter.api.cfg.Items
import org.alter.api.cfg.Objs

enum class RockType(
    val level: Int, val experience: Double, val reward: Int, val respawnDelay: Int, val varrockArmourAffected: Int,
    val lowChance: Int, val highChance: Int, val objectIds: Map<Int, Int> = emptyMap()
) {
    ESSENCE(
        level = 1,
        experience = 5.0,
        reward = Items.RUNE_ESSENCE,
        respawnDelay = -1,  //Doesn't de-spawn
        varrockArmourAffected = -1,
        lowChance = 255,
        highChance = 392,
        objectIds = mapOf(
            Pair(Objs.RUNE_ESSENCE_34773, -1)
        )
    ),
    CLAY(
        level = 1,
        experience = 5.0,
        reward = Items.CLAY,
        respawnDelay = 2,   //1.2 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 128,
        highChance = 400,
        objectIds = mapOf(
            Pair(Objs.CLAY_ROCKS_11362, -1),
            Pair(Objs.CLAY_ROCKS_11363, -1)
        )
    ),
    COPPER(
        level = 1,
        experience = 17.5,
        reward = Items.COPPER_ORE,
        respawnDelay = 4,   //2.4 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 102,    //TODO This is grabbed from 2011Scape, use runescape.wiki crowd-source info where available
        highChance = 379,   //TODO This is grabbed from 2011Scape, use runescape.wiki crowd-source info where available
        objectIds = mapOf(
            Pair(Objs.COPPER_ROCKS_10079, Objs.ROCKS_10081),
            Pair(Objs.COPPER_ROCKS_10943, -1),
            Pair(Objs.COPPER_ROCKS_11161, -1),
            Pair(Objs.COPPER_ROCKS_37944, -1)
        )
    ),
    TIN(
        level = 1,
        experience = 17.5,
        reward = Items.TIN_ORE,
        respawnDelay = 4,   //2.4 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 102,    //TODO This is grabbed from 2011Scape, use runescape.wiki crowd-source info where available
        highChance = 379,   //TODO This is grabbed from 2011Scape, use runescape.wiki crowd-source info where available
        objectIds = mapOf(
            Pair(Objs.TIN_ROCKS_10080, Objs.ROCKS_10081),
            Pair(Objs.TIN_ROCKS_11360, -1),
            Pair(Objs.TIN_ROCKS_11361, -1),
            Pair(Objs.TIN_ROCKS_37945, -1)
        )
    ),
    BLURITE(
        level = 10,
        experience = 17.5,
        reward = Items.BLURITE_ORE,
        respawnDelay = 42,  //~25 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 106,    //TODO This is grabbed from 2011Scape, use runescape.wiki crowd-source info where available
        highChance = 365,   //TODO This is grabbed from 2011Scape, use runescape.wiki crowd-source info where available
        objectIds = mapOf(
            Pair(Objs.BLURITE_ROCKS_11378, -1),
            Pair(Objs.BLURITE_ROCKS_11379, -1)
        )
    ),
    IRON(
        level = 15,
        experience = 35.0,
        reward = Items.IRON_ORE,
        respawnDelay = 9,   //5.4 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 110,
        highChance = 350,
        objectIds = mapOf(
            Pair(Objs.IRON_ROCKS_11364, -1),
            Pair(Objs.IRON_ROCKS_11365, -1),
            Pair(Objs.IRON_ROCKS_36203, -1),
            Pair(Objs.IRON_ROCKS_42833, -1)
        )
    ),

    ;

    companion object {
        val values = enumValues<RockType>()
    }
}