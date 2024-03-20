package org.alter.plugins.content.area.tutorial_island.npcs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland

val MINING_INSTRUCTOR = Npc(Npcs.MINING_INSTRUCTOR, Tile(3081, 9505, 0), world)
MINING_INSTRUCTOR.respawns = true
MINING_INSTRUCTOR.walkRadius  = 2
world.spawn(MINING_INSTRUCTOR)
TutorialIsland.MINING_INSTRUCTOR_NPC_INDEX = MINING_INSTRUCTOR.index

on_npc_option(Npcs.MINING_INSTRUCTOR, "talk-to") {
    player.queue {
        val state = player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)

        when (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)) {
            260 -> {
                chatNpc("Hi there. You must be new around here. So what do I<br>call you? 'Newcomer' seems so impersonal, and if we're<br>going to be working together, I'd rather call you by<br>name.", animation = Animation.CHAT_DEFAULT)
                chatPlayer("You can call me ${player.displayname}.", animation = Animation.CHAT_QUIZ1)
                chatNpc("Ok then, ${player.displayname}. My name is Dezzick and I'm a<br>miner by trade. Let's teach you how to mine.", animation = Animation.CHAT_QUIZ2)
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 270)
                chatNpc("Mining is very simple, all you need is a pickaxe. The<br>rocks around here contain tin and copper. Why don't you get started by mining some?", animation = Animation.CHAT_NEUTRAL2)
                player.inventory.add(Items.BRONZE_PICKAXE, 1)
                player.focusTab(GameframeTab.INVENTORY)
                itemMessageBox("The mining instructor gives you a <col=000080>bronze pickaxe<col=000000>.", item = Items.BRONZE_PICKAXE, amountOrZoom = 400)
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 300)
                TutorialIsland.process(player)
            }
        }

        if(state == 260) {
            chatNpc(npc = Npcs.MINING_INSTRUCTOR, title = "Mining Instructor", animation = 591, message = "Hi there. You must be new around here. So what do I<br>call you? 'Newcomer' seems so impersonal, and if we're<br>going to be working together, I'd rather call you by<br>name.")
            chatPlayer(message = "You can call me ${player.displayname}.", animation = 588)
            chatNpc(npc = Npcs.MINING_INSTRUCTOR, title= "Mining Instructor", animation = 589, message = "Ok then, ${player.displayname}. My name is Dezzick and I'm a<br>miner by trade. Let's teach you how to mine.")
            chatNpc(npc = Npcs.MINING_INSTRUCTOR, title = "Mining Instructor", animation = 570, message = "Mining is very simple, all you need is a pickaxe. The<br>rocks around here contain tin and copper. Why don't<br>you get started by mining some? If you're unsure<br>which is which, you can prospect them to find out.")

            player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 270)
            player.inventory.add(Items.BRONZE_PICKAXE, 1)
            player.focusTab(GameframeTab.INVENTORY)
            itemMessageBox(item = Items.BRONZE_PICKAXE, message = "The mining instructor gives you a <col=000080>bronze pickaxe<col=000000>.")
            TutorialIsland.process(player)
        }
    }
}