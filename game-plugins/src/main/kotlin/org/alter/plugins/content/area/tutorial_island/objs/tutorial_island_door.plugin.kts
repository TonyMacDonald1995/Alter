package org.alter.plugins.content.area.tutorial_island.objs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland
import org.alter.plugins.content.area.tutorial_island.objs.door_module.init_tutorial_door
import org.alter.plugins.content.area.tutorial_island.objs.door_module.init_tutorial_double_door

val DOOR_CLOSED = 9398
val DOOR_OPEN = 1539

//on_obj_option(DOOR_CLOSED, "open") {
//    val obj = player.getInteractingGameObj()
//
//    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 10) {
//        player.queue(TaskPriority.WEAK) {
//            this.messageBox("You need to talk to the Gielinor Guide before you are allowed to proceed through this door.")
//            TutorialIsland.process(player)
//        }
//    } else {
//        player.queue(TaskPriority.STRONG) {
//            world.queue {
//                val open = DynamicObject(DOOR_OPEN, 0, 1, obj.tile)
//                world.remove(obj)
//                world.spawn(open)
//                wait(2)
//                world.remove(open)
//                world.spawn(DynamicObject(obj))
//            }
//
//            player.playSound(62, 1, 0)
//
//            player.walkTo(this, 3098, 3107, MovementQueue.StepType.NORMAL, false)
//            player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 20)
//            player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 2)
//            TutorialIsland.process(player)
//        }
//    }
//}
//Start Room Door
init_tutorial_door {
    config {
        obj = Objs.DOOR_9398
        replaceObj = Objs.DOOR_1539
        replaceRot = 1
        replaceTile = Tile(3097, 3107, 0)
        doorTile = Tile(3098, 3107, 0)
        requiredVarpState = 10
        outsideTile = Tile(3098, 3107, 0)
        insideTile = Tile(3097, 3107, 0)
        afterActionVarpState = 20
        errorMsg = "You need to talk to the Gielinor Guide before you are allowed to proceed through this door."
        openSound = Sound.DOOR_OPEN
    }
}

//Chef Door Entrance
init_tutorial_door {
    config {
        obj = Objs.DOOR_9709
        replaceObj = Objs.DOOR_1539
        doorTile = Tile(3079,3084,0)
        replaceTile = Tile(3078, 3084, 0)
        requiredVarpState = 130
        outsideTile = Tile(3079,3084,0)
        insideTile = Tile(3078, 3084, 0)
        afterActionVarpState = 140
        openSound = Sound.DOOR_OPEN
    }
}
//Chef Door Exit
init_tutorial_door {
    config {
        obj = Objs.DOOR_9710
        replaceObj = Objs.DOOR_1539
        doorTile = Tile(3072,3090,0)
        replaceTile = Tile(3073, 3090, 0)
        replaceRot = 3
        requiredVarpState = 170
        outsideTile = Tile(3072,3090,0)
        insideTile = Tile(3073, 3090, 0)
        afterActionVarpState = 200
        errorMsg = "You need to finish the Master Chef's tasks first."
        openSound = Sound.DOOR_OPEN
    }
}
//Quest Guide Door
init_tutorial_door {
    config {
        obj = Objs.DOOR_9716
        replaceObj = Objs.DOOR_1539
        replaceTile = Tile(3086, 3125, 0)
        replaceRot = 0
        doorTile = Tile(3086, 3126, 0)
        requiredVarpState = 200
        outsideTile = Tile(3086, 3126, 0)
        insideTile = Tile(3086, 3125, 0)
        afterActionVarpState = 220
        openSound = Sound.DOOR_OPEN
    }
}

init_tutorial_double_door {
    config {
        objLeft = Objs.GATE_9717
        objRight = Objs.GATE_9718
        replaceObjLeft = Objs.GATE_1573
        replaceObjRight = Objs.GATE_1573
        replaceTileLeft = Tile(3095, 9503, 0)
        replaceTileRight = Tile(3095, 9502, 0)
        replaceRotLeft = 1
        replaceRotRight = 3
        doorTileLeft = Tile(3094, 9503, 0)
        doorTileRight = Tile(3094, 9502, 0)
        outsideTileLeft = Tile(3094, 9503, 0)
        outsideTileRight = Tile(3094, 9502, 0)
        insideTileLeft = Tile(3095, 9503, 0)
        insideTileRight = Tile(3095, 9502, 0)
        requiredVarpState = 360
        afterActionVarpState = 370
        openSound = Sound.IRON_DOOR_OPEN
        errorMsg = "You need to finish with Mining and Smithing first."
    }
}