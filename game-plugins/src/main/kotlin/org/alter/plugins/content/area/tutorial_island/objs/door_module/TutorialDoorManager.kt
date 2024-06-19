package org.alter.plugins.content.area.tutorial_island.objs.door_module

import org.alter.api.cfg.Npcs
import org.alter.api.cfg.Varp
import org.alter.api.ext.*
import org.alter.game.model.ForcedMovement
import org.alter.game.model.LockState
import org.alter.game.model.MovementQueue
import org.alter.game.model.Tile
import org.alter.game.model.entity.DynamicObject
import org.alter.game.model.entity.Player
import org.alter.game.model.queue.QueueTask
import org.alter.plugins.content.area.tutorial_island.TutorialIsland

object TutorialDoorManager {
    val tutorialDoors: ArrayList<TutorialDoor> = arrayListOf()
    var tutorialDoubleDoors: ArrayList<TutorialDoubleDoor> = arrayListOf()

    fun openDoor(player: Player, door: TutorialDoor) {
        /**
         * Check if player has [door.requiredVarpState]
         */
        if (door.requiredVarpState > player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)) {
            if (door.errorMsg != null) {
                player.queue { messageBox(door.errorMsg!!) }
            }
            return
        }

        player.queue { openDoorTask(this, door) }
    }

    fun openDoubleDoor(player: Player, door: TutorialDoubleDoor) {
        if (door.requiredVarpState > player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)) {
            if (door.errorMsg != null) {
                player.queue { messageBox(door.errorMsg!!) }
            }
            return
        }

        player.queue { openDoubleDoorTask(this, door) }
    }

    private fun openDoorTask(it: QueueTask, door: TutorialDoor) {
        val player = it.player
        val world = it.player.world
        val doorObj = world.getObject(door.doorTile, 0)

        if(door.openSound != -1) {
            player.playSound(door.openSound, 1, 0)
        }

        world.queue {
            val openDoor = DynamicObject(door.replaceObj, 0, door.replaceRot, door.replaceTile?: door.doorTile)
            player.lock = LockState.DELAY_ACTIONS
            world.remove(doorObj!!)
            world.spawn(openDoor)
            wait(1)
            if (player.tile.getDistance(door.insideTile) < player.tile.getDistance(door.outsideTile)) {
                player.walkTo(this, door.outsideTile, MovementQueue.StepType.NORMAL, true)
                wait { player.tile == door.outsideTile }
            } else {
                player.walkTo(this, door.insideTile, MovementQueue.StepType.NORMAL, true)
                wait { player.tile == door.insideTile }
            }
            wait(1)
            val closedDoor = DynamicObject(door.obj, 0, doorObj.rot, door.doorTile)
            world.remove(openDoor)
            world.spawn(closedDoor)
            player.unlock()
        }

        if (door.afterActionVarpState > player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION))
            player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, door.afterActionVarpState)

        TutorialIsland.process(player)
    }

    private fun openDoubleDoorTask(it: QueueTask, door: TutorialDoubleDoor) {
        val player = it.player
        val world = it.player.world
        val doorObjLeft = player.world.getObject(door.doorTileLeft, 0)
        val doorObjRight = player.world.getObject(door.doorTileRight, 0)
        val interactingDoor = if (player.getInteractingGameObj() == doorObjLeft) "left" else "right"

        if (door.openSound != -1) {
            player.playSound(door.openSound, 1, 0)
        }

        world.queue {
            val openDoorLeft = DynamicObject(door.replaceObjLeft, 0, door.replaceRotLeft, door.replaceTileLeft?: door.doorTileLeft)
            val openDoorRight = DynamicObject(door.replaceObjRight, 0, door.replaceRotRight, door.replaceTileRight?: door.doorTileRight)

            player.lock = LockState.DELAY_ACTIONS

            world.remove(doorObjLeft!!)
            world.remove(doorObjRight!!)

            world.spawn(openDoorLeft)
            world.spawn(openDoorRight)

            if (interactingDoor == "left") {
                if (player.tile.getDistance(door.insideTileLeft) < player.tile.getDistance(door.outsideTileLeft)) {
                    player.walkTo(this, door.outsideTileLeft, MovementQueue.StepType.NORMAL, true)
                    wait { player.tile == door.outsideTileLeft }
                } else {
                    player.walkTo(this, door.insideTileLeft, MovementQueue.StepType.NORMAL, true)
                    wait { player.tile == door.insideTileLeft }
                }
            } else {
                if (player.tile.getDistance(door.insideTileRight) < player.tile.getDistance(door.outsideTileRight)) {
                    player.walkTo(this, door.outsideTileRight, MovementQueue.StepType.NORMAL, true)
                    wait { player.tile == door.outsideTileRight }
                } else {
                    player.walkTo(this, door.insideTileRight, MovementQueue.StepType.NORMAL, true)
                    wait { player.tile == door.insideTileRight }
                }
            }

            wait(1)
            val closedDoorLeft = DynamicObject(door.objLeft, 0, doorObjLeft.rot, door.doorTileLeft)
            val closedDoorRight = DynamicObject(door.objRight, 0, doorObjRight.rot, door.doorTileRight)

            world.remove(openDoorLeft)
            world.remove(openDoorRight)

            world.spawn(closedDoorLeft)
            world.spawn(closedDoorRight)

            player.unlock()
        }

        if (door.afterActionVarpState > player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION))
            player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, door.afterActionVarpState)

        TutorialIsland.process(player)
    }
}