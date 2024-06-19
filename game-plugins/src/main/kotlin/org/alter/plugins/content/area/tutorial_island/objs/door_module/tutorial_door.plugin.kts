package org.alter.plugins.content.area.tutorial_island.objs.door_module

TutorialDoorManager.tutorialDoors.forEach { door ->
    on_obj_option(door.obj, "open") {
        TutorialDoorManager.openDoor(player, door)
    }
}

TutorialDoorManager.tutorialDoubleDoors.forEach { door ->
    on_obj_option(door.objLeft, "open") {
        TutorialDoorManager.openDoubleDoor(player, door)
    }
    on_obj_option(door.objRight, "open") {
        TutorialDoorManager.openDoubleDoor(player, door)
    }
}