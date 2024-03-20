package org.alter.plugins.content.area.tutorial_island.objs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland

on_obj_option(Objs.LADDER_9726, "climb-down") {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 250) {
        player.queue {
            player.animate(827)
            wait(3)
            player.moveTo(Tile(3088, 9520, 0))
            player.animate(0)
            player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 260)
            TutorialIsland.process(player)
        }
    } else {
        player.queue {
            chatNpc(npc = Npcs.QUEST_GUIDE, title = "Quest Guide", message = "I don't think you're ready to go down there yet.", animation = Animation.CHAT_QUIZ1)
        }
    }
}