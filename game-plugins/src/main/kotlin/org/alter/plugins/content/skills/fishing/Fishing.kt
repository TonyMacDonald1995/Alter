package org.alter.plugins.content.skills.fishing

import org.alter.api.Skills
import org.alter.api.cfg.Varp
import org.alter.api.ext.*
import org.alter.game.model.entity.Player
import org.alter.game.model.queue.QueueTask

class Fishing(val player: Player, private val spot: FishingSpots) {
    suspend fun startFishing(it: QueueTask) {
        if (!canFish()) {
            player.animate(-1)
            return
        }

        while (true) {
            player.world.plugins.executeOnStartFishing(player, player.getInteractingNpc().id)
            player.animate(spot.animation)
            it.wait(2)

            if (!canFish()) {
                player.animate(-1)
                break
            }

            val lvl = player.getSkills().getCurrentLevel(Skills.FISHING)

            val randomFish = getRandomFish()

            if (lvl.interpolateCheck(minChance = randomFish.lowChance, maxChance = randomFish.highChance)) {
                player.world.plugins.executeOnCatchFish(player, player.getInteractingNpc().id)

                if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) >= 1000) {
                    player.message("You catch some fish.")
                }

                if (spot.baitId != -1) {
                    player.inventory.remove(spot.baitId, 1)
                }
                player.inventory.add(randomFish.fishItem, 1)
                player.addXp(Skills.FISHING, randomFish.xp)
            }
            it.wait(2)
        }
    }

    private fun getRandomFish(): Fish {
        return spot.fish.filter { player.getSkills().getBaseLevel(Skills.FISHING) >= it.level }.random()
    }

    private fun canFish(): Boolean {
        if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 40) {
            player.queue { messageBox("You cannot fish here yet. You must progress further in the tutorial.") }
            return false
        }
        if (!hasLevel()) {
            player.message("You need at least level ${minLevel()} fishing to catch fish here.")
            return false
        }
        if (!hasTool()) {
            player.message("You do not have the required tool to fish here.")
            return false
        }
        if (spot.baitId != -1 && !player.inventory.contains(spot.baitId)) {
            player.message("You do not have any bait to fish here.")
            return false
        }
        if (player.inventory.isFull) {
            player.message("You do not have enough space to hold any more fish.")
            return false
        }
        if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 50) {
            return false
        }

        return true
    }

    private fun hasLevel(): Boolean {
        var hasLevel = false
        spot.fish.forEach { fish ->
            if (player.getSkills().getCurrentLevel(Skills.FISHING) >= fish.level)
                hasLevel = true
        }
        return hasLevel
    }

    private fun minLevel(): Int {
        var minLevel = 0
        spot.fish.forEach { fish ->
            if (fish.level > minLevel)
                minLevel = fish.level
        }
        return minLevel
    }

    private fun hasTool(): Boolean {
        return player.inventory.contains(spot.toolId)
    }
}