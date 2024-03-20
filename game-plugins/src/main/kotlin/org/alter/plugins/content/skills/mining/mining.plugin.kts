package org.alter.plugins.content.skills.mining

import org.alter.api.ext.getInteractingGameObj
import org.alter.api.ext.player

//private val ORES = setOf(
//    // TODO: Need to find the alternate emptyOreId rock
//    Ore(OreType.RUNE_ESSENCE, obj = 34773, emptyOreId = 0), // X = 2911 Y = 4832 - Essence Mine
//
//    Ore(OreType.CLAY, obj = Objs.CLAY_ROCKS, emptyOreId = 2704),
//    Ore(OreType.CLAY, obj = Objs.CLAY_ROCKS_11363, emptyOreId = 11391),
//
//    Ore(OreType.COPPER, obj = Objs.COPPER_ROCKS_10943, emptyOreId = 2704),
//    Ore(OreType.COPPER, obj = Objs.COPPER_ROCKS_11161, emptyOreId = 11391),
//
//    Ore(OreType.TIN, obj = Objs.TIN_ROCKS, emptyOreId = 10081), //Tutorial Island Tin
//    Ore(OreType.TIN, obj = Objs.TIN_ROCKS_11360, emptyOreId = 2704),
//    Ore(OreType.TIN, obj = Objs.TIN_ROCKS_11361, emptyOreId = 11391),
//
//    Ore(OreType.BLURITE, obj = Objs.BLURITE_ROCKS, emptyOreId = 2704),
//    Ore(OreType.BLURITE, obj = Objs.BLURITE_ROCKS_11379, emptyOreId = 11391),
//
//    // TODO: Find Limestone emptyOreId
//    Ore(OreType.LIMESTONE, obj = Objs.LIMESTONE_ROCK, emptyOreId = 0), // Need to figure out emptyOreId
//
//    Ore(OreType.IRON, obj = Objs.IRON_ROCKS, emptyOreId = 2704),
//    Ore(OreType.IRON, obj = Objs.IRON_ROCKS_11365, emptyOreId = 11391),
//
//    Ore(OreType.SILVER, obj = Objs.SILVER_ROCKS, emptyOreId = 2704),
//    Ore(OreType.SILVER, obj = Objs.SILVER_ROCKS_11369, emptyOreId = 11391),
//
//    Ore(OreType.COAL, obj = Objs.COAL_ROCKS_11366, emptyOreId = 2704),
//    Ore(OreType.COAL, obj = Objs.COAL_ROCKS_11367, emptyOreId = 11391),
//
//    Ore(OreType.SANDSTONE1, obj = Objs.SANDSTONE_ROCKS, emptyOreId = 2704),
//
//    Ore(OreType.GOLD, obj = Objs.GOLD_ROCKS, emptyOreId = 2704),
//    Ore(OreType.GOLD, obj = Objs.GOLD_ROCKS_11371, emptyOreId = 11391),
//
//    Ore(OreType.GEMSTONE1, obj = Objs.GEM_ROCKS_11381, emptyOreId = 11391),
//    Ore(OreType.GEMSTONE1, obj = Objs.GEM_ROCKS, emptyOreId = 11391),
//
//    Ore(OreType.GRANITE1, obj = Objs.GRANITE_ROCKS, emptyOreId = 2704),
//
//    Ore(OreType.MITHRIL, obj = Objs.MITHRIL_ROCKS, emptyOreId = 2704),
//    Ore(OreType.MITHRIL, obj = Objs.MITHRIL_ROCKS_11373, emptyOreId = 11391),
//
//    Ore(OreType.ADAMANTITE, obj = Objs.ADAMANTITE_ROCKS, emptyOreId = 2704),
//    Ore(OreType.ADAMANTITE, obj = Objs.ADAMANTITE_ROCKS_11375, emptyOreId = 11391),
//
//    Ore(OreType.RUNITE, obj = Objs.RUNITE_ROCKS_11376, emptyOreId = 2704),
//    Ore(OreType.RUNITE, obj = Objs.RUNITE_ROCKS_11377, emptyOreId = 11391)
//)

val rockValues = RockType.values
val rockObjects = RockType.objects
val depletedRockSet = rockObjects.map { id ->
    world.definitions.get(ObjectDef::class.java, id).depleted
}.toSet()

rockObjects.forEach { rock ->
    on_obj_option(obj = rock, option = 1) {
        val obj = player.getInteractingGameObj()
        val rockType = rockValues.find { obj.id in it.objectIds } ?: return@on_obj_option
        player.queue {
            Mining.mineRock(this, obj, rockType)
        }
    }
}

depletedRockSet.forEach { depletedRock ->
    on_obj_option(obj = depletedRock, option = 1) {
        player.animate(-1)
        player.playSound(Sound.PROSPECT)
        player.filterableMessage("There is currently no ore available in this rock.")
    }
}