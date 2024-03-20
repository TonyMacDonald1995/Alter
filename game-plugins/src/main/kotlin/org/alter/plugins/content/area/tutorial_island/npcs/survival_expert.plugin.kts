package org.alter.plugins.content.area.tutorial_island.npcs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland

val SURVIVAL_EXPERT = Npc(Npcs.SURVIVAL_EXPERT, Tile(3103, 3096, 0), world)
SURVIVAL_EXPERT.respawns = true
SURVIVAL_EXPERT.walkRadius = 2
world.spawn(SURVIVAL_EXPERT)
TutorialIsland.SURVIVAL_EXPERT_NPC_INDEX = SURVIVAL_EXPERT.index

on_npc_option(Npcs.SURVIVAL_EXPERT, "talk-to") {
    player.queue(TaskPriority.WEAK) {

        when (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)) {
            20 -> {
                chatNpc(npc = Npcs.SURVIVAL_EXPERT, title = "Survival Expert", message = "Hello there, newcomer. My name is Brynna. My job is<br>to teach you about the skills you can use to survive in<br>this world.")
                chatNpc(npc = Npcs.SURVIVAL_EXPERT, title = "Survival Expert", message = "The first skills we're going to look at is Fishing. There's<br>some shrimp in this pond here. Let's try and catch<br>some.")
                player.inventory.add(Items.SMALL_FISHING_NET, 1)
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 30)
                itemMessageBox(item = Items.SMALL_FISHING_NET, message = "The survival expert gives you a <col=000080>small fishing net</col>.")
                TutorialIsland.process(player)
            }
            40 -> {
                chatNpc(npc = Npcs.SURVIVAL_EXPERT, title = "Survival Expert", message = "Hello again. First up, we're going to do some fishing. There's some shrimp in this pond here. Let's try and catch some.")
                TutorialIsland.process(player)
            }
            50 -> {
                chatNpc(npc = Npcs.SURVIVAL_EXPERT, title = "Survival Expert", message = "Hello again. You should take a look at that menu before we continue.")
                TutorialIsland.process(player)
            }
            60 -> {
                chatPlayer(message = "I've managed to catch some shrimp.")
                chatNpc(npc = Npcs.SURVIVAL_EXPERT, title = "Survival Expert", message = "Excellent work. Now that you have some shrimp, you're<br>going to want to cook them. To do that you'll need a<br>fire. This brings us on to the Woodcutting and<br>Firemaking skills.")
                player.inventory.add(Items.BRONZE_AXE, 1)
                player.inventory.add(Items.TINDERBOX, 1)
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 70)
                itemMessageBox(item = Items.BRONZE_AXE, message = "The survival expert gives you a <col=000080>bronze axe</col> and a <br><col=000080>tinderbox</col>.")
                TutorialIsland.process(player)
            }
            70 -> {
                chatNpc(npc = Npcs.SURVIVAL_EXPERT, title = "Survival Expert", message = "Next up you need to make a fire. First, you'll need to cut down a tree to get some logs.")
                TutorialIsland.process(player)
            }
            80 -> {
                chatNpc(npc = Npcs.SURVIVAL_EXPERT, title = "Survival Expert", message = "Now that you have some shrimp, you're going to want to cook them. To do that, you'll need a fire.")
                TutorialIsland.process(player)
            }
            90 -> {
                chatNpc(npc = Npcs.SURVIVAL_EXPERT, title = "Survival Expert", message = "Now that you have some shrimp, you're going to want to cook them. All you need to do is use them on a fire.")
                TutorialIsland.process(player)
            }
            in 120..1000 -> {
                chatNpc(npc = Npcs.SURVIVAL_EXPERT, title = "Survival Expert", message = "Hello again. Is there something you'd like to hear more about?")
                choice@while (true) {
                    when (this.options("Tell me about my skills again.", "Tell me about Woodcutting again.", "Tell me about Firemaking again.", "Tell me about Fishing again", "Tell me about Cooking again.", "Nothing thanks.")) {
                        1 -> {
                            chatPlayer("Tell me about my skills again.")
                            chatNpc("Every skill is listed in the skills menu. Here you can see what your current levels are and how much experience you have.")
                            chatNpc("As you move your mouse over the various skills the small yellow popup box will show you the exact amount of experience you have and how much is needed to get to the next level.")
                            chatNpc("You can also click on a skill to open up the relevant skillguide. In the skillguide, you can see all the unlocks available in that skill.")
                            chatNpc("Is there anything else you'd like to hear more about?")
                        }
                        2 -> {
                            chatPlayer("Tell me about Woodcutting again.")
                            chatNpc("Woodcutting, eh? Don't worry, newcomer, it's really very easy. Simply equip your axe and click on a nearby tree to chop away.")
                            chatNpc("As you explore the mainland you will discover many different kinds of trees that will require different Woodcutting levels to chop down.")
                            chatNpc("Logs are not only useful for making fires. Many archers use the skill known as Fletching to craft their own bows and arrows from trees.")
                            chatNpc("Is there anything else you'd like to hear more about?")
                        }
                        3 -> {
                            chatPlayer("Tell me about Firemaking again.")
                            chatNpc("Certainly, newcomer. When you have logs simply use your tinderbox on them. If successful, you will start a fire.")
                            chatNpc("You can also set fire to logs you find lying on the floor already, and some other things can also be set alight...")
                            chatNpc("A tinderbox is always a useful item to keep around!")
                            chatNpc("Is there anything else you'd like to hear more about?")
                        }
                        4 -> {
                            chatPlayer("Tell me about Fishing again.")
                            chatNpc("Ah, yes. Fishing! Fishing is undoubtedly one of the more popular hobbies here in Gielinor!")
                            chatNpc("Whenever you see sparkling waters, you can be sure there's probably some good fishing to be had there!")
                            chatNpc("Not only are fish absolutely delicious when cooked, they will also heal lost health.")
                            chatNpc("I would recommend everybody has a go at Fishing at least once in their lives!")
                            chatNpc("Is there anything else you'd like to hear more about?")
                        }
                        5 -> {
                            chatPlayer("Tell me about Cooking again.")
                            chatNpc("Yes, the most basic of survival techniques. Most simple foods can be used on a fire to cook them. If you're feeling a bit fancier, you can also use a range to cook the food instead.")
                            chatNpc("Eating cooked food will restore lost health. The harder something is to cook, the more it will heal you.")
                            chatNpc("Is there anything else you'd like to hear more about?")
                        }
                        6 -> {
                            chatPlayer("Nothing thanks.")
                            break@choice
                        }
                    }
                }
                TutorialIsland.process(player)
            }
        }
    }
}