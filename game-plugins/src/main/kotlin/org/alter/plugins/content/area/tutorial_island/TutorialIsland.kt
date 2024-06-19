package org.alter.plugins.content.area.tutorial_island

import org.alter.api.InterfaceDestination
import org.alter.api.cfg.Song
import org.alter.api.cfg.Varbit
import org.alter.api.cfg.Varp
import org.alter.api.ext.*
import org.alter.game.model.LockState
import org.alter.game.model.attr.APPEARANCE_SET_ATTR
import org.alter.game.model.attr.DISPLAY_NAME_SET_ATTR
import org.alter.game.model.entity.Player
import org.alter.game.model.queue.TaskPriority
import org.alter.plugins.content.interfaces.displayname.openDisplayNameInterface

object TutorialIsland {

    var GUIDE_NPC_INDEX: Int = 0
    var SURVIVAL_EXPERT_NPC_INDEX: Int = 0
    var MASTER_CHEF_NPC_INDEX: Int = 0
    var QUEST_GUIDE_NPC_INDEX: Int = 0
    var MINING_INSTRUCTOR_NPC_INDEX: Int = 0
    var COMBAT_INSTRUCTOR_NPC_INDEX: Int = 0


    val TAB_CHILD_IDS: HashMap<InterfaceDestination, Array<Int>> = hashMapOf(
        Pair(InterfaceDestination.SETTINGS, arrayOf(35,46,38)),
        Pair(InterfaceDestination.SKILLS, arrayOf(49,59,58)),
        Pair(InterfaceDestination.QUEST_ROOT, arrayOf(50,60,59)),
        Pair(InterfaceDestination.INVENTORY, arrayOf(51,61,53))
    )

    val HIDDEN_INTERFACES = arrayOf(
        InterfaceDestination.ATTACK,
        InterfaceDestination.SKILLS,
        InterfaceDestination.QUEST_ROOT,
        InterfaceDestination.INVENTORY,
        InterfaceDestination.EQUIPMENT,
        InterfaceDestination.PRAYER,
        InterfaceDestination.MAGIC,
        InterfaceDestination.SOCIAL,
        InterfaceDestination.ACCOUNT_MANAGEMENT,
        InterfaceDestination.CLAN_CHAT,
        InterfaceDestination.SETTINGS,
        InterfaceDestination.EMOTES,
        InterfaceDestination.MUSIC
    )

    private fun init(player: Player, state: Int) {
        //player.openInterface(interfaceId = 614, dest = getDisplayComponentId(player.interfaces.displayMode), child = 1, modal = false)
        player.openInterface(InterfaceDestination.TUTORIAL_ISLAND_PROGRESS)

        if(state >= 3) {
            player.openInterface(InterfaceDestination.SETTINGS)
        }

        if(state >= 30) {
            player.openInterface(InterfaceDestination.INVENTORY)
            player.setInterfaceEvents(interfaceId = 149, component = 0, range = 0 .. 27,
                setting = arrayOf(
                    InterfaceEvent.ClickOp2, InterfaceEvent.ClickOp3, InterfaceEvent.ClickOp4, InterfaceEvent.ClickOp6,
                    InterfaceEvent.ClickOp7, InterfaceEvent.ClickOp10, InterfaceEvent.UseOnGroundItem, InterfaceEvent.UseOnNpc, InterfaceEvent.UseOnObject,
                    InterfaceEvent.UseOnPlayer, InterfaceEvent.UseOnInventory, InterfaceEvent.UseOnComponent, InterfaceEvent.DRAG_DEPTH1, InterfaceEvent.DragTargetable,
                    InterfaceEvent.ComponentTargetable
                )
            )
        }

        if(state >= 50) {
            player.openInterface(InterfaceDestination.SKILLS)
        }

        if(state >= 230) {
            player.setVarbit(Varbit.PLAYER_SUMMARY_FOCUS_TAB, 1)
            player.openInterface(InterfaceDestination.QUEST_ROOT)
        }
    }

    fun process(player: Player) {
        val state = player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION)

        this.init(player, state)

        when (state) {
            1 -> {
                if (player.attr[DISPLAY_NAME_SET_ATTR] == null) {
                    player.lock = LockState.FULL_WITH_LOGOUT
                    player.staticDialog("<col=0000ff>Setting your name</col><br>Before you get started, you'll need to set a display name. Please use the open interface to set one.")
                    player.openDisplayNameInterface()
                }
                if (player.attr[DISPLAY_NAME_SET_ATTR] == true && player.attr[APPEARANCE_SET_ATTR] == null) {
                    player.queue(TaskPriority.WEAK) {
                        wait(2)
                        player.staticDialog("<col=0000ff>Setting your appearance</col><br>Before you get started, you'll need to set the appearance of your character. Please use the open interface to set your appearance.")
                        selectAppearance()
                    }
                }
                if (player.attr[DISPLAY_NAME_SET_ATTR] == true && player.attr[APPEARANCE_SET_ATTR] == true) {
                    player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 2)
                    player.unlock()
                    process(player)
                }
            }

            2 -> {
                player.staticDialog("<col=0000ff>Getting started</col><br>Before you begin, have a read through the controls guide in the top left of the screen. When you're ready to get started, click on the Gielinor Guide. He is indicated by a flashing yellow arrow.")
                player.sendHintArrow(1, GUIDE_NPC_INDEX, 0, 0)
            }
            3 -> {
                player.sendHintArrow(0, 0, 0, 0)
                player.setVarbit(Varbit.FLASH_TAB, 12)
                player.staticDialog("<col=0000ff>Settings menu</col><br>Please click on the flashing spanner icon found on the bottom right of your screen. This will display your settings menu.")
            }

            7 -> {
                player.sendHintArrow(1, GUIDE_NPC_INDEX, 0, 0)
                player.setVarbit(Varbit.FLASH_TAB, -1)
                player.staticDialog("<col=0000ff>Settings menu</col><br>On the side panel, you can now see a variety of game settings. You can also click the all settings button to see all available settings. Talk to the Gielinor Guide to continue.")
            }

            10 -> {
                player.sendHintArrow(3, 3098, 3107, 128)
                player.staticDialog("<col=0000ff>Moving on</col><br>It's time to meet your first instructor. To continue, all you need to do is click on the door. It's indicated by a flashing yellow arrow. Remember, you can use your arrow keys to rotate the camera.")
            }

            20 -> {
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 2)
                player.sendHintArrow(1, SURVIVAL_EXPERT_NPC_INDEX, 0, 0)
                player.staticDialog("<col=0000ff>Moving around</col><br>Follow the path to find the next instructor. Clicking on the ground will walk you to that point. You can also move around by clicking a point on the minimap in the top right corner. Talk to the survival expert to continue the tutorial.")
            }

            30 -> {
                player.setVarbit(Varbit.FLASH_TAB, 4)
                player.sendHintArrow(0, 0, 0, 0)
                player.staticDialog("<col=0000ff>You've been given an item</col><br>To view the item you've been given, you'll need to open your inventory. To do so, click on the flashing backpack icon to the right hand side of your screen.")
            }

            40 -> {
                player.setVarbit(Varbit.FLASH_TAB, -1)
                player.sendHintArrow(2, 3101, 3092, 0)
                player.staticDialog("<col=0000ff>Fishing</col><br>This is your inventory. You can view all of your items here, including the net you've just been given. Let's use it to catch some shrimp. To start fishing, just click on the sparkling fishing spot, indicated by the flashing arrow.]")
            }

            50 -> {
                player.setVarbit(Varbit.FLASH_TAB, 2)
                player.sendHintArrow(0, 0, 0, 0)
                player.staticDialog("<col=0000ff>You've gained some experience</col><br>Click on the flashing bar graph icon near the inventory button to see your skills menu.")
            }

            60 -> {
                player.setVarbit(Varbit.FLASH_TAB, -1)
                player.sendHintArrow(1, SURVIVAL_EXPERT_NPC_INDEX, 0, 0)
                player.staticDialog("<col=0000ff>Skills and Experience</col><br>On this menu you can view your skills. Your skills can be leveled up by earning experience, which is gained by performing various activities. As you level up your skills, you will earn new unlocks. Speak to the survival expert to continue.")
            }

            70 -> {
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 3)
                player.sendHintArrow(4, 3099, 3095, 150)
                player.staticDialog("<col=0000ff>Woodcutting</col><br>It's time to cook your shrimp. However, you require a fire to do that which means you need some logs. You can cut down trees using your Woodcutting skill, all you need is an axe. Give it a go by clicking on one of the trees in the area.")
            }

            80 -> {
                player.sendHintArrow(0, 0, 0, 0)
                player.staticDialog("<col=0000ff>Firemaking</col><br>Now that you have some logs, it's time to light a fire. First, click on the tinderbox in your inventory. Then, with the tinderbox highlighted, click on the logs to use the tinderbox on them.")
            }

            90 -> {
                player.staticDialog("<col=0000ff>Cooking</col><br>Now it's time to get cooking. To do so, click on the shrimp in your inventory. Then, with the shrimp highlighted, click on a fire to cook them. If you look at the top left of the screen, you'll see the instructions that you're giving to your character.")
            }

            120 -> {
                player.sendHintArrow(4, 3089, 3092, 65)
                player.staticDialog("<col=0000ff>Moving on</col><br>Well done, you've just cooked your first meal! Speak to the survival expert if you want a recap, otherwise you can move on. Click on the gate shown and follow the path. Remember, you can use your arrow keys to rotate the camera.")
            }

            130 -> {
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 4)
                player.sendHintArrow(3, 3079, 3084, 128)
                player.staticDialog("<col=0000ff>Moving on</col><br>Follow the path until you get to the door with the yellow arrow above it. Click on the door to open it. Remember that you can also move around by clicking on the minimap in the top right.")
            }

            140 -> {
                player.sendHintArrow(1, MASTER_CHEF_NPC_INDEX, 0, 0)
                player.staticDialog("<col=0000ff>Cooking</col><br>Talk to the chef indicated. He will teach you the more advanced aspects of Cooking such as combining ingredients.")
            }

            150 -> {
                player.sendHintArrow(0, 0, 0, 0)
                player.staticDialog("<col=0000ff>Making dough</col><br>This is the base for many meals. To make dough you must mix flour with water. To do so, click on the flour in your inventory. Then, with the flour highlighted, click on the water to combine them into dough.")
            }

            160 -> {
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 5)
                player.sendHintArrow(4, 3075, 3081, 65)
                player.staticDialog("<col=0000ff>Cooking dough</col><br>Now you have made the dough, you can bake it into some bread. To do so, just click on the indicated range.")
            }

            170 -> {
                player.sendHintArrow(4, 3072, 3090, 128)
                player.staticDialog("<col=0000ff>Moving on</col><br>Well done! You've baked your first loaf of bread. As you gain experience in Cooking, you will be able to make other things like pies and cakes. You can now use the next door to move on. If you need a recap on anything, talk to the master chef.")
            }

            200 -> {
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 6)
                player.sendHintArrow(5, 3086, 3126, 128)
                player.staticDialog("<col=0000ff>Fancy a run?</col><br>When navigating the world, you can either run or walk. Running is faster but you can't run for long as you'll soon run out of energy. You can use the flashing orb next to the minimap to toggle running. Why not try it as you head to the next section?")
            }

            210 -> {
                player.staticDialog("<col=0000ff>Moving on</col><br>Follow the path to the next guide. When you get there, click on the door to pass through it. Remember, you can use your arrow keys to rotate the camera.")
            }

            220 -> {
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 7)
                player.sendHintArrow(1, QUEST_GUIDE_NPC_INDEX, 0, 0)
                player.staticDialog("<col=0000ff>Quests</col><br>It's time to learn about quests! Just talk to the Quest Guide to get started.")
            }

            230 -> {
                player.setVarbit(Varbit.FLASH_TAB, 3)
                player.sendHintArrow(0, 0, 0, 0)
                player.staticDialog("<col=0000ff>Quest journal</col><br>Click on the flashing icon to the left of your inventory.")
            }

            240 -> {
                player.setVarbit(Varbit.FLASH_TAB, -1)
                player.sendHintArrow(1, QUEST_GUIDE_NPC_INDEX, 0, 0)
                player.staticDialog("<col=0000ff>Quest journal</col><br>This is your quest journal. It lists every quest in the game. Talk to the quest guide again for an explanation on how it works.")
            }

            250 -> {
                player.sendHintArrow(2, 3088, 3119, 5)
                player.staticDialog("<col=0000ff>Moving on</col><br>It's time to enter some caves. Click on the ladder to go down to the next area.")
            }

            260 -> {
                player.playSong(Song.SCAPE_CAVE.id)
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 8)
                player.sendHintArrow(1, MINING_INSTRUCTOR_NPC_INDEX, 0, 0)
                player.staticDialog("<col=0000ff>Mining and Smithing</col><br>Next let's get you a weapon, or more to the point, you can make your first weapon yourself. Don't panic, the mining instructor will help you. Talk to him and he'll tell you all about it.")
            }
            300 -> {
                player.sendHintArrow(2, 3077, 9504, 0)
                player.staticDialog("<col=0000ff>Mining</col><br>It's quite simple really. To mine a rock, all you need to do is click on it. First up, try mining some tin.")
            }
            310 -> {
                player.sendHintArrow(2, 3083, 9501, 0)
                player.staticDialog("<col=0000ff>Mining</col><br>Now that you have some tin ore, you just need some copper. To mine a rock, all you need to do is click on it.")
            }
            320 -> {
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 9)
                player.sendHintArrow(2, 3078, 9495, 0)
                player.staticDialog("<col=0000ff>Smelting</col><br>You now have some tin ore and some copper ore. You can smelt these into a bronze bar. To do so, just click on the indicated furnace. Try it now.")
            }
            330 -> {
                player.sendHintArrow(1, MINING_INSTRUCTOR_NPC_INDEX, 0, 0)
                player.staticDialog("<col=0000ff>Smelting</col><br>You've made a bronze bar! Speak to the mining instructor and he'll show you how to make it into a weapon.")
            }
            340 -> {
                player.sendHintArrow(2, 3075, 9499, 0)
                player.staticDialog("<col=0000ff>Smithing a dagger</col><br>To smith you'll need a hammer and enough metal bars to make the desired item, as well as a handy anvil. To start the process, click on the anvil, or alternatively use the bar on it.")
            }
            350 -> {
                player.staticDialog("<col=0000ff>Smithing a dagger</col><br>Now you have the smithing menu open, you will see a list of all the things you can make. Only the dagger can be made at your skill level; this is shown by the white text under it. You'll need to select the dagger to continue.")
            }
            360 -> {
                player.sendHintArrow(2, 3094, 9503, 0)
                player.staticDialog("<col=0000ff>Moving on</col><br>Congratulations, you've made your first weapon. Now it's time to move on. Go through the gates shown by the arrow. Remember, you may need to move the camera to see your surroundings. Speak to the mining instructor for a recap at any time.")
            }
            370 -> {
                player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 10)
                player.sendHintArrow(1, COMBAT_INSTRUCTOR_NPC_INDEX, 0, 0)
                player.staticDialog("<col=0000ff>Combat</col><br>In this area you will find out about melee and ranged combat. Speak to the guide and he will tell you all about it.")
            }
            390 -> {
                //TODO Open Equipment Tab
            }
            400 -> {
                //TODO Open Equipment Stats
            }
            405 -> {
                //TODO Equip Bronze Dagger
            }
            410 -> {
                //TODO Talk to Vannaka
            }
            420 -> {
                //TODO Equip Wooden Shield and Bronze Sword
            }
            430 -> {
                //TODO Open Combat Styles Tab
            }
            440 -> {
                //TODO Enter Rat Cage
            }
            450 -> {
                //TODO Attack Rat with Melee
            }
            460 -> {
                //TODO Wait for Rat to Die
            }
            470 -> {
                //TODO Exit Rat Cage and talk to Vannaka
            }
            480 -> {
                //TODO Equip Shortbow and Bronze arrows and attack Rat
            }
            490 -> {
                //TODO Wait for Rat to die
            }
            500 -> {
                //TODO Exit combat area
            }
            510 -> {
                //TODO Open Bank
            }
            520 -> {
                //TODO Close Bank and Open Poll Booth
            }
            530 -> {
                //TODO Talk to Account Guide
            }
            531 -> {
                //TODO Open Account Management Tab
            }
            532 -> {
                //TODO Talk to Account Guide
            }
            540 -> {
                //TODO Exit Account Guide's Room
            }
            550 -> {
                //TODO Talk to Brother Brace
            }
            560 -> {
                //TODO Open Prayer Tab
            }
            570 -> {
                //TODO Talk to Brother Brace
            }
            580 -> {
                //TODO Open Friends List
            }
            600 -> {
                //TODO Talk to Brother Brace
            }
            610 -> {
                //TODO Exit Chapel
            }
            620 -> {
                //TODO Talk to Magic Instructor
            }
            630 -> {
                //TODO Open Spell Book
            }
            640 -> {
                //TODO Talk to Magic Instructor
            }
            650 -> {
                //TODO Kill Chicken with Air Strike
            }
            670 -> {
                //TODO Talk to Magic Instructor
            }
            1000 -> {
                //TODO DONE
            }
        }
    }
}