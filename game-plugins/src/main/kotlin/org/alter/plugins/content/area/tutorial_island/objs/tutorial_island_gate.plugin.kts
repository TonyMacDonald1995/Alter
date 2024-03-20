package org.alter.plugins.content.area.tutorial_island.objs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland

val gate = world.getObject(Tile(3089, 3092, 0), 0)
val ext = world.getObject(Tile(3089, 3091, 0), 0)

val OBJS = intArrayOf(Objs.GATE_9470, Objs.GATE_9708)

OBJS.forEach { obj ->
    on_obj_option(obj, "Open") {
        if(player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) >= 120) {
            player.queue {
                val newGate = DynamicObject(1566, 0, 1, Tile(3090, 3092, 0))
                val newExt = DynamicObject(1565, 0, 1, Tile(3091, 3092, 0))

                val repGate = DynamicObject(gate!!, 83)
                val repExt = DynamicObject(ext!!, 83)

                world.queue {
                    val oldGate = DynamicObject(9470, 0, 2, Tile(3089, 3092, 0))
                    val oldExt = DynamicObject(9708, 0, 2, Tile(3089, 3091, 0))
                    world.remove(gate)
                    world.remove(ext)
                    world.spawn(repGate)
                    world.spawn(repExt)
                    world.spawn(newGate)
                    world.spawn(newExt)
                    wait(3)
                    world.remove(repGate)
                    world.remove(repExt)
                    world.remove(newGate)
                    world.remove(newExt)
                    world.spawn(oldGate)
                    world.spawn(oldExt)
                }

                player.playSound(Sound.GATE_OPEN)

                val tile = player.tile.transform( if(player.tile.x <= 3089) 1 else -1, 0)
                player.walkTo(tile, MovementQueue.StepType.NORMAL, false)

                if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 130)
                    player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 130)

                TutorialIsland.process(player)
            }
        } else {
            player.queue { messageBox("You need to talk to the Survival Guide and complete her tasks before you are allowed to proceed through this gate.") }
        }
    }
}