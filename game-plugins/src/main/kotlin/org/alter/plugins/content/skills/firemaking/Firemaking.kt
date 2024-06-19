package org.alter.plugins.content.skills.firemaking

import org.alter.api.Skills
import org.alter.api.cfg.Items
import org.alter.api.cfg.Objs
import org.alter.api.cfg.Sound
import org.alter.api.cfg.Varp
import org.alter.api.ext.*
import org.alter.game.model.Direction
import org.alter.game.model.MovementQueue
import org.alter.game.model.Tile
import org.alter.game.model.entity.DynamicObject
import org.alter.game.model.entity.GroundItem
import org.alter.game.model.entity.Player
import org.alter.game.model.queue.QueueTask
import org.alter.plugins.content.area.tutorial_island.Tutorial_island_plugin
import org.alter.plugins.content.area.tutorial_island.staticDialog

object Firemaking {


    suspend fun burnLog(it: QueueTask, log: LogData) {
        if(!canBurn(it.player, log)) {
            return
        }
        val player = it.player

        // Drop the logs on the ground
        player.inventory.remove(log.log)
        val logDrop = GroundItem(log.log, 1, player.tile, player)
        player.world.spawn(logDrop)
        if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 80) {
            player.staticDialog("<col=0000ff>Please wait</col><br>Your character is now attempting to light a fire. This should only take a few seconds.")
        } else {
            player.filterableMessage("You attempt to light the logs.")
        }

        while(true) {
            player.animate(733)
            it.wait(2)

            if(!canBurn(player,log)) {
                player.animate(-1)
                break
            }

            val level = player.getSkills().getCurrentLevel(Skills.FIREMAKING)
            if(level.interpolateCheck(minChance = 64, maxChance = 512)) {

                if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) >= 1000) {
                    player.filterableMessage("The fire catches and the logs begin to burn.")
                } else if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 80) {
                    player.world.plugins.executeEvent(player, Tutorial_island_plugin.FireStartedEvent)
                }

                player.addXp(Skills.FIREMAKING, log.xp)

                val world = player.world

                world.queue {
                    val fire = DynamicObject(Objs.FIRE_26185, 10, 0, logDrop.tile)
                    val randBurnTicks = (150..300).random()
                    world.remove(logDrop)
                    world.spawn(fire)
                    player.playSound(Sound.FIRE_LIT)
                    wait(randBurnTicks)
                    world.remove(fire)
                    val ashes = GroundItem(Items.ASHES, 1, fire.tile)
                    world.spawn(ashes)

                    // De-spawn after 2 min
                    wait(200)
                    world.remove(ashes)
                }

                player.animate(-1)

                var targetWalkTile = Tile(player.tile.x-1, player.tile.z, player.tile.height)
                if(player.world.collision.isBlocked(targetWalkTile, Direction.WEST, false)) {
                    targetWalkTile = Tile(player.tile.x+1, player.tile.z, player.tile.height)
                    if(player.world.collision.isBlocked(targetWalkTile, Direction.EAST, false)) {
                        targetWalkTile = player.tile
                    }
                }

                if(targetWalkTile.getDistance(player.tile) > 0) {
                    player.walkTo(targetWalkTile, MovementQueue.StepType.NORMAL, false)
                }
                break
            }
            it.wait(6)
        }
    }

    private fun canBurn(player: Player, log: LogData): Boolean {
        if(player.getSkills().getCurrentLevel(Skills.FIREMAKING) < log.level) {
            player.filterableMessage("You need a Firemaking level of at least ${log.level} to light this.")
            return false
        }

        if(!player.inventory.contains(Items.TINDERBOX)) {
            player.filterableMessage("You do not have any fire source to light this.")
            return false
        }

        return true
    }
}