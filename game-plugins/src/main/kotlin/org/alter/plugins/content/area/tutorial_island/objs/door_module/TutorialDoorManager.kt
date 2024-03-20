package org.alter.plugins.content.area.tutorial_island.objs.door_module

import org.alter.api.cfg.Npcs
import org.alter.api.cfg.Varp
import org.alter.api.ext.*
import org.alter.game.model.ForcedMovement
import org.alter.game.model.MovementQueue
import org.alter.game.model.Tile
import org.alter.game.model.entity.DynamicObject
import org.alter.game.model.entity.Player
import org.alter.game.model.queue.QueueTask
import org.alter.plugins.content.area.tutorial_island.TutorialIsland

object TutorialDoorManager {
    val tutorialDoors: ArrayList<TutorialDoor> = arrayListOf()

    fun openDoor(player: Player, door: TutorialDoor) {
        /**
         * Check if player has [door.requiredVarpState]
         */
        if (door.requiredVarpState > player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)) {
            if (door.errorMsg != null) {
                player.queue {
                    messageBox(door.errorMsg!!)
                }
            }
            return
        }

        player.queue { openDoorTask(this, door) }
    }

    private fun openDoorTask(it: QueueTask, door: TutorialDoor) {
        val player = it.player
        val world = it.player.world
        val doorObj = world.getObject(door.doorTile, 0)

        if(door.openSound != -1) {
            player.playSound(door.openSound, 1, 0)
        }

        world.queue {
            val openDoor = DynamicObject(door.replaceObj, 0, door.replaceRot, if (door.replaceTile != Tile(0,0,0)) door.replaceTile else door.doorTile)
            world.remove(doorObj!!)
            world.spawn(openDoor)
            wait(1)
            if (player.tile.getDistance(door.insideTile) < player.tile.getDistance(door.outsideTile)) {
                player.walkTo(this, door.outsideTile, MovementQueue.StepType.NORMAL, true)
            } else {
                player.walkTo(this, door.insideTile, MovementQueue.StepType.NORMAL, true)
            }
            wait(1)
            val closedDoor = DynamicObject(door.obj, 0, doorObj.rot, door.doorTile)
            world.remove(openDoor)
            world.spawn(closedDoor)
        }

        if (door.afterActionVarpState > player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION))
            player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, door.afterActionVarpState)

        TutorialIsland.process(player)
    }
}