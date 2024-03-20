package org.alter.plugins.content.area.tutorial_island.npcs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland

val QUEST_GUIDE = Npc(Npcs.QUEST_GUIDE, Tile(3086, 3122, 0), world)
QUEST_GUIDE.respawns = true
QUEST_GUIDE.walkRadius = 2
world.spawn(QUEST_GUIDE)
TutorialIsland.QUEST_GUIDE_NPC_INDEX = QUEST_GUIDE.index

on_npc_option(Npcs.QUEST_GUIDE, "talk-to") {
    player.queue {
        when (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)) {
            220 -> {
                chatNpc("Ah. Welcome, adventurer. I'm here to tell you all about<br>quests. Let's start by opening your quest list.", animation = Animation.CHAT_QUIZ2)
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 230)
                TutorialIsland.process(player)
            }
            230 -> {
                chatNpc("Have you not opened that menu yet?")
            }
            240 -> {
                chatNpc("Now that you have the quest list open, you can see all<br>the quests within it. Clicking one of these quests will<br>display some more information on it.", animation = Animation.CHAT_QUIZ3)
                chatNpc("If you haven't started the quest, it will tell you where<br>to begin and what requirements you need. If the quest is<br>in progress, it will remind you what to do next.", animation = Animation.CHAT_QUIZ3)
                chatNpc("It's very easy to find quest start points. Just look out<br>for the quest icon on your minimap. You should<br>see one marking this house.", animation = Animation.CHAT_QUIZ3)
                itemMessageBox("The minimap in the top right corner of the screen has various icons to show different points of interest. Look for the icon to the left to find quest start points.", item = Items.ITEM_5092, amountOrZoom = 400)
                chatNpc("The quests themselves can vary greatly from collecting<br>beads to hunting down dragons. Completing quests will<br>reward you with all sorts of things, such as new areas<br>and better weapons!", animation = Animation.CHAT_DEFAULT)
                chatNpc("There's not a lot more I can tell you about questing.<br>You have to experience the thrill of it yourself to fully<br>understand. Let me know if you want a recap,<br>otherwise you can move on.", animation = Animation.CHAT_DEFAULT)
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 250)
                TutorialIsland.process(player)
            }
        }
    }
}