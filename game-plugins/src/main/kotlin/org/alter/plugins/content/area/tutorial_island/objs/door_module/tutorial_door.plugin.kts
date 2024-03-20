package org.alter.plugins.content.area.tutorial_island.objs.door_module

TutorialDoorManager.tutorialDoors.forEach { door ->
    on_obj_option(door.obj, "open") {
        TutorialDoorManager.openDoor(player, door)
    }
}