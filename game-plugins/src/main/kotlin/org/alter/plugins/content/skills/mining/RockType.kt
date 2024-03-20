package org.alter.plugins.content.skills.mining

import org.alter.api.cfg.Items
import org.alter.api.cfg.Objs

enum class RockType(
    val level: Int, val experience: Double, val reward: Int, val respawnDelay: Int, val varrockArmourAffected: Int,
    val lowChance: Int, val highChance: Int, val objectIds: Array<Int>
) {
    ESSENCE(
        level = 1,
        experience = 5.0,
        reward = Items.RUNE_ESSENCE,
        respawnDelay = -1,  //Doesn't de-spawn
        varrockArmourAffected = -1,
        lowChance = 255,
        highChance = 392,
        objectIds = arrayOf(Objs.RUNE_ESSENCE_34773)
    ),
    CLAY(
        level = 1,
        experience = 5.0,
        reward = Items.CLAY,
        respawnDelay = 2,   //1.2 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 128,
        highChance = 400,
        objectIds = arrayOf(Objs.CLAY_ROCKS_11362, Objs.CLAY_ROCKS_11363)
    ),
    COPPER(
        level = 1,
        experience = 17.5,
        reward = Items.COPPER_ORE,
        respawnDelay = 4,   //2.4 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 128,    //TODO This is best guess based on clay use runescape.wiki crowd-source info where available
        highChance = 400,   //TODO This is best guess based on clay use runescape.wiki crowd-source info where available
        objectIds = arrayOf(Objs.COPPER_ROCKS_10079, Objs.COPPER_ROCKS_10943, Objs.COPPER_ROCKS_11161, Objs.COPPER_ROCKS_37944)
    ),
    TIN(
        level = 1,
        experience = 17.5,
        reward = Items.TIN_ORE,
        respawnDelay = 4,   //2.4 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 128,    //TODO This is best guess based on clay use runescape.wiki crowd-source info where available
        highChance = 400,   //TODO This is best guess based on clay use runescape.wiki crowd-source info where available
        objectIds = arrayOf(Objs.TIN_ROCKS_10080, Objs.TIN_ROCKS_11360, Objs.TIN_ROCKS_11361, Objs.TIN_ROCKS_37945)
    ),
    BLURITE(
        level = 10,
        experience = 17.5,
        reward = Items.BLURITE_ORE,
        respawnDelay = 42,  //~25 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 128,    //TODO This is best guess based on clay use runescape.wiki crowd-source info where available
        highChance = 400,   //TODO This is best guess based on clay use runescape.wiki crowd-source info where available
        objectIds = arrayOf(Objs.BLURITE_ROCKS_11378, Objs.BLURITE_ROCKS_11379)
    ),
    IRON(
        level = 15,
        experience = 35.0,
        reward = Items.IRON_ORE,
        respawnDelay = 9,   //5.4 seconds
        varrockArmourAffected = Items.VARROCK_ARMOUR_1,
        lowChance = 110,
        highChance = 350,
        objectIds = arrayOf(Objs.IRON_ROCKS_11364, Objs.IRON_ROCKS_11365, Objs.IRON_ROCKS_36203, Objs.IRON_ROCKS_42833)
    ),

    ;

    companion object {
        val values = enumValues<RockType>()
        val objects = RockType.values().flatMap { it.objectIds.toList() }
    }
}