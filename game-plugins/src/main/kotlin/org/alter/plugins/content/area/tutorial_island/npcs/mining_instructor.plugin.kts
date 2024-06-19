package org.alter.plugins.content.area.tutorial_island.npcs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland

val MINING_INSTRUCTOR = Npc(Npcs.MINING_INSTRUCTOR, Tile(3081, 9505, 0), world)
MINING_INSTRUCTOR.respawns = true
MINING_INSTRUCTOR.walkRadius  = 2
world.spawn(MINING_INSTRUCTOR)
TutorialIsland.MINING_INSTRUCTOR_NPC_INDEX = MINING_INSTRUCTOR.index

on_npc_option(Npcs.MINING_INSTRUCTOR, "talk-to") {
    player.queue {

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
            300 -> {
                chatNpc("The rocks around here contain tin and copper. You should try mining some.") //TODO Verify
            }
            310 -> {
                //TODO
            }
            320 -> {
                //TODO
            }
            330 -> {
                chatPlayer("I have a bronze bar. What now?", animation = Animation.CHAT_HAPPY1)
                chatNpc("Now that you've got a bar, you can smith it into a<br>weapon. To smith something, you need a hammer and<br>an anvil. There's some anvils just here that you can<br>use. See if you can make a bronze dagger.", animation = Animation.CHAT_NEUTRAL3)
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 340)
                player.inventory.add(Items.HAMMER)
                itemMessageBox("The mining instructor gives you a <col=000080>hammer<col=000000>.", item = Items.HAMMER, amountOrZoom = 400)
                TutorialIsland.process(player)
            }
        }
    }
}