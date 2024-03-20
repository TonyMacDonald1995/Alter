package org.alter.plugins.content.area.tutorial_island.npcs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland

/**
 * Chat progression with the gielinor guide in the first room of
 * tutorial island.
 * Reliving this make me so nostalgic
 */

val GIELINOR_GUIDE = Npc(Npcs.GIELINOR_GUIDE, Tile(3094, 3109, 0),  world)
GIELINOR_GUIDE.respawns = true
GIELINOR_GUIDE.walkRadius = 3
world.spawn(GIELINOR_GUIDE)
TutorialIsland.GUIDE_NPC_INDEX = GIELINOR_GUIDE.index

on_npc_option(Npcs.GIELINOR_GUIDE, "talk-to") {
    player.queue(TaskPriority.WEAK) {
        when (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)) {
            in 0..2 -> {
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "Greetings! I see you are a new arrival to the world of Gielinor. My job is to welcome all new visitors. So Welcome!")
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "You have already learned the first thing needed to succeed in this world: talking to other people!")
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "You will find many inhabitants of this world have useful things to say to you. By clicking on them you can talk to them.")
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "Before we get going, if you could be so kind to let me know how much experience you have with Old School Runescape, that would be wonderful!")
                this.chatNpc(message = "To answer, simply click your chosen answer on the following screen.")
                val playerExperience = when (this.options("I am brand new! This is my first time here.", "I've played in the past, but not recently.", "I am an experienced player.", title = "What's your experience with Old School Runescape?")) {
                    1 -> this.chatPlayer(title = this.player.displayname, message = "I am brand new! This is my first time here.")
                    2 -> this.chatPlayer(title = this.player.displayname, message = "I've played in the past, but not recently.")
                    3 -> this.chatPlayer(title = this.player.displayname, message = "I am an experienced player.")
                    else -> {}
                }
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "Wonderful! Thank you.")
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 3)
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "Now then, let's start by looking at your settings menu.")
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "You will notice a flashing icon of a spanner. Please click on this to continue the tutorial.")
                TutorialIsland.process(player)
            }
            3 -> {
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "Now then, let's start by looking at your settings menu.")
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "You will notice a flashing icon of a spanner. Please click on this to continue the tutorial.")
                TutorialIsland.process(player)
            }
            7 -> {
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "Looks like you're making good progress! The menu<br>you've just opened is one of many. You'll learn about<br>the rest as you progress through the tutorial.")
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "Anyway, I'd say it's time for you to go and meet your first instructors!")
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 10)
                TutorialIsland.process(player)

            }
            10 -> {
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "Welcome back. You have already learnt the first thing needed to succeed in this world: talking to other people!")
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "You will find many inhabitants of this world have useful things to say to you. By clicking on them, you can talk to them.")
                this.chatNpc(npc = Npcs.GIELINOR_GUIDE, title = "Gielinor Guide", message = "To continue the tutorial go through that door over there and speak to your first instructor!")
                TutorialIsland.process(player)
            }
        }
    }
}