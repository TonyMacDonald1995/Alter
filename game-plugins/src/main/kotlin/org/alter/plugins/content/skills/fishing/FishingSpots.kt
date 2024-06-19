package org.alter.plugins.content.skills.fishing

import org.alter.api.cfg.Animation
import org.alter.api.cfg.Items
import org.alter.api.cfg.Npcs

enum class FishingSpots(val spotEntityId: List<Int>, val option: List<String>, val toolId: Int, val baitId: Int, val animation: Int, vararg val fish: Fish) {
    TUTORIAL_NET(
        spotEntityId = listOf(Npcs.FISHING_SPOT_3317),
        option = listOf("net"),
        toolId = Items.SMALL_FISHING_NET,
        baitId = -1,
        animation = Animation.FISHING_NET,
        Fish.SHRIMP
    ),
    SMALL_NET1(
        spotEntityId = listOf(Npcs.FISHING_SPOT_1514, Npcs.FISHING_SPOT_1517, Npcs.FISHING_SPOT_1518, Npcs.FISHING_SPOT_1521, Npcs.FISHING_SPOT_1523,
                               Npcs.FISHING_SPOT_1524, Npcs.FISHING_SPOT_1525, Npcs.FISHING_SPOT_1528, Npcs.FISHING_SPOT_1530, Npcs.FISHING_SPOT_1544,
                               Npcs.FISHING_SPOT_3913, Npcs.FISHING_SPOT_7155, Npcs.FISHING_SPOT_7459, Npcs.FISHING_SPOT_7462, Npcs.FISHING_SPOT_7467,
                               Npcs.FISHING_SPOT_7469, Npcs.FISHING_SPOT_7947),
        option = listOf("net"),
        toolId = Items.SMALL_FISHING_NET,
        baitId = -1,
        animation = Animation.FISHING_NET,
        Fish.SHRIMP,
        Fish.ANCHOVIES
    );

    //fun getFish(): Array<out Fish> { return fish }
}