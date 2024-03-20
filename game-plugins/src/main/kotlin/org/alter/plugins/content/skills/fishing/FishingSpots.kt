package org.alter.plugins.content.skills.fishing

import org.alter.api.cfg.Animation
import org.alter.api.cfg.Items
import org.alter.api.cfg.Npcs

enum class FishingSpots(val spotEntityId: Array<Int>, val option: String, val toolId: Int, val baitId: Int, val animation: Int, vararg fish: Fish) {
    TUTORIAL_NET(
        spotEntityId = arrayOf(Npcs.FISHING_SPOT_3317),
        option = "net",
        toolId = Items.SMALL_FISHING_NET,
        baitId = -1,
        animation = Animation.FISHING_NET,
        Fish.SHRIMP
    ),
    SMALL_NET1(
        spotEntityId = arrayOf(Npcs.FISHING_SPOT_1514, Npcs.FISHING_SPOT_1517, Npcs.FISHING_SPOT_1518, Npcs.FISHING_SPOT_1521, Npcs.FISHING_SPOT_1523,
                               Npcs.FISHING_SPOT_1524, Npcs.FISHING_SPOT_1525, Npcs.FISHING_SPOT_1528, Npcs.FISHING_SPOT_1530, Npcs.FISHING_SPOT_1544,
                               Npcs.FISHING_SPOT_3913, Npcs.FISHING_SPOT_7155, Npcs.FISHING_SPOT_7459, Npcs.FISHING_SPOT_7462, Npcs.FISHING_SPOT_7467,
                               Npcs.FISHING_SPOT_7469, Npcs.FISHING_SPOT_7947),
        option = "small net",
        toolId = Items.SMALL_FISHING_NET,
        baitId = -1,
        animation = Animation.FISHING_NET,
        Fish.SHRIMP,
        Fish.ANCHOVIES
    );

    private val fish = fish

    fun getFish(): Array<out Fish> { return this.fish }
}