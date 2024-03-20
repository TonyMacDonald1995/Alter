package org.alter.plugins.content.area.tutorial_island.npcs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland

val MASTER_CHEF = Npc(Npcs.MASTER_CHEF, Tile(3075, 3084, 0), world)
MASTER_CHEF.respawns = true
MASTER_CHEF.walkRadius = 2
world.spawn(MASTER_CHEF)
TutorialIsland.MASTER_CHEF_NPC_INDEX = MASTER_CHEF.index

on_npc_option(Npcs.MASTER_CHEF, "talk-to") {
    player.queue {
        val state = player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)
        when(state) {
            140 -> {
                chatNpc(npc = Npcs.MASTER_CHEF, title = "Master Chef", message = "Ah! Welcome, newcomer. I am the Master Chef, Lev. It<br>is here that I will teach you how to cook food truly fit<br>for a king.", animation = 590)
                chatPlayer(message = "I already know how to cook. Brynna taught me just<br>now.", animation = 615)
                chatNpc(npc = Npcs.MASTER_CHEF, title = "Master Chef", animation = 607, message = "Hahahahahaha! You call THAT cooking? Some shrimp<br>on an open log fire? Oh, no, no, no. I am going to<br>teach you the fine art of cooking bread.")

                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 150)
                player.inventory.add(Items.POT_OF_FLOUR_2516, 1)
                player.inventory.add(Items.BUCKET_OF_WATER, 1)

                itemMessageBox(item = Items.POT_OF_FLOUR, message = "The master chef gives you some <col=000080>flour<col=000000> and some <col=000080>water<col=000000>.")
                doubleItemMessageBox(message = "The master chef gives you some <col=000080>flour<col=000000> and some <col=000080>water<col=000000>.", item1 = Items.POT_OF_FLOUR_2516, item2 = Items.BUCKET_OF_WATER, amount1 = 400, amount2 = 400)
                TutorialIsland.process(player)
            }
            150, 160 -> {
                chatNpc(npc = Npcs.MASTER_CHEF, title = "Master Chef", message = "Time for you to learn the fine art of cooking bread.")
            }
            in 170..1000 -> {
                chatNpc(npc = Npcs.MASTER_CHEF, title = "Master Chef", message = "Do you need something?")
                choice@while (true) {
                    when (options("Tell me about making dough again.", "Tell me about range cooking again.", "Nothing thanks.")) {
                        1 -> {
                            chatPlayer("Tell me about making dough again.")
                            chatNpc("It's quite simple. Just use a pot of flour on a bucket of water, or vice versa, and you'll make dough. You can fill a bucket with water at any sink.")
                            chatNpc("Do you need anything else?")
                        }
                        2 -> {
                            chatPlayer("Tell me about range cooking again.")
                            chatNpc("The range is the only place you can cook a lot of the more complex foods in Gielinor. To cook on a range, all you need to do is click on it. You'll need to make sure you have the required items in your inventory though.")
                        }
                        3 -> {
                            chatPlayer("Nothing thanks.")
                        }
                    }
                }
            }
        }
    }
}