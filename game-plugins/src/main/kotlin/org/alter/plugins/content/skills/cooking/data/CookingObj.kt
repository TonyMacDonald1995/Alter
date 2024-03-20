package org.alter.plugins.content.skills.cooking.data

import org.alter.api.cfg.Sound

/**
 * @author Kyle Escobar
 *
 * Repersents the world objects that can be used to cook food.
 *
 * @param objId         The [GameObject] id
 * @param animation     The animation id to be performed while cooking.
 * @param isRange       True of false whether or not the object acts like a cooking range. (true unless it is a fire source)
 * @param sound         The sound id to be played when cooking
 */
enum class CookingObj(val objId: Int, val animation: Int = 896, val isRange: Boolean = true, val sound: Int = Sound.OVEN) {
    LUMBRIDGE_CASTLE_RANGE(objId = 114, isRange = true, sound = Sound.OVEN),
    ALKHARID_RANGE(objId = 26181, isRange = true, sound = Sound.OVEN),
    VARROCK_RANGE(objId = 7183, isRange = true, sound = Sound.OVEN),
    VARROCK_OVEN(objId = 7421, isRange = true, sound = Sound.OVEN),
    EDGE_RANGE(objId = 12269, isRange = true, sound = Sound.OVEN),
    RIMMININGTON_OVEN(objId = 9682, isRange = true, sound = Sound.OVEN),
    COOKING_POT(objId = 26180, isRange = false),
    FIRE(objId = 26185, animation = 897, isRange = false, sound = Sound.FRY);


    companion object {
        /**
         * Cached array of enums
         */
        val values = enumValues<CookingObj>()

        /**
         * Map of cooking objects to their id's
         */
        val definitions = values.associate { it.objId to it }
    }
}