package org.alter.plugins.content.area.tutorial_island

import org.alter.game.event.Event
import org.alter.game.model.attr.NEW_ACCOUNT_ATTR
import org.alter.game.model.attr.TUTORIAL_CATCH_FISH
import org.alter.game.model.attr.TUTORIAL_STARTED_FISHING
import org.alter.plugins.content.skills.cooking.data.CookingFood
import org.alter.plugins.content.skills.fishing.FishingSpots

object ChopTreeEvent : Event
object CookedBreadEvent : Event
object CookedShrimpEvent : Event
object CreateBreadDoughEvent : Event
object FireStartedEvent : Event
object ToggleRunEvent : Event
object TreeCutDownEvent : Event
object MineTinEvent : Event
object MineCopperEvent : Event
object CreateBronzeDaggerEvent : Event
object OpenSmithingInterfaceEvent : Event


on_login {

    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 1000 && player.attr[NEW_ACCOUNT_ATTR] == null) {
        TutorialIsland.process(player)
        player.playSong(62)
        player.canDropItems = false
    }

    val newAccount = player.attr[NEW_ACCOUNT_ATTR] ?: return@on_login

    if (newAccount) {
        player.setComponentText(interfaceId = 239, component = 6, text = "Newbie Melody")
        initTutorialIsland(player)
    }
}

fun initTutorialIsland(player: Player) {

    player.runClientScript(2644)

    player.runClientScript(828, 0)

    player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 1)
    player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESS_BAR, 0)

    player.playSong(62)

    player.canDropItems = false

    TutorialIsland.process(player)
}

on_item_on_item(Items.POT_OF_FLOUR_2516, Items.BUCKET_OF_WATER) {
    player.inventory.remove(Items.POT_OF_FLOUR_2516)
    player.inventory.remove(Items.BUCKET_OF_WATER)
    player.inventory.add(Items.POT)
    player.inventory.add(Items.BUCKET)
    player.inventory.add(Items.BREAD_DOUGH)
    player.triggerEvent(CreateBreadDoughEvent)
}

on_obj_option(Objs.RANGE_9736, "cook") {
    if (!player.inventory.contains(Items.BREAD_DOUGH)) {
        player.queue { messageBox("You haven't got anything suitable for cooking! The master chef can help you out with that.") }
    }
    player.queue { messageBox("<col=0000ff>Please wait</col><br>Your character is now attempting to bake some bread. This will only take a few seconds.") }
    player.queue(TaskPriority.STRONG) {
        player.lock = LockState.DELAY_ACTIONS
        player.animate(Animation.COOKING_ON_RANGE_896)
        wait(5)
        player.animate(-1)
        player.playSound(Sound.OVEN)
        player.inventory.remove(Items.BREAD_DOUGH)
        player.inventory.add(Items.BREAD)
        player.addXp(Skills.COOKING, CookingFood.BREAD.xp)
        player.triggerEvent(CookedBreadEvent)
        player.unlock()
    }
}

on_interface_close(APPEARANCE_INTERFACE_ID) {
    TutorialIsland.process(player)
}

FishingSpots.TUTORIAL_NET.spotEntityId.forEach { entityId ->
    on_start_fishing(entityId) {
        if (player.attr[TUTORIAL_STARTED_FISHING] == null) {
            player.attr.put(TUTORIAL_STARTED_FISHING, true)
            player.staticDialog(
                "<col=0000ff>Please wait</col><br>Your character is now attempting to catch some shrimp. Sit back for a moment while he does all the hard work.",
                overlay = false
            )
        }
    }
}

FishingSpots.TUTORIAL_NET.spotEntityId.forEach { entityId ->
    on_catch_fish(entityId) {
        if (player.attr[TUTORIAL_CATCH_FISH] == null) {
            player.attr.put(TUTORIAL_CATCH_FISH, true)
            player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 50)
            TutorialIsland.process(player)
        }
    }
}

listenTabClick(InterfaceDestination.SETTINGS) {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 3) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 7)
        TutorialIsland.process(player)
    }
}

listenTabClick(InterfaceDestination.INVENTORY) {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 30) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 40)
        TutorialIsland.process(player)
    }
}

listenTabClick(InterfaceDestination.SKILLS) {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 50) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 60)
        TutorialIsland.process(player)
    }
}

listenTabClick(InterfaceDestination.QUEST_ROOT) {
    if(player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 230) {
        player.openInterface(InterfaceDestination.QUEST_ROOT.interfaceId, 33, 399, 1)
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 240)
        TutorialIsland.process(player)
    }
}

on_event(ChopTreeEvent::class.java) {
    player.staticDialog("<col=0000ff>Please wait</col><br>Your character is now attempting to cut down the tree. Sit back for a moment while he does all the hard work.")
}

on_event(CookedBreadEvent::class.java) {
    player.queue { itemMessageBox(item = Items.BREAD, message = "You manage to bake some bread.") }
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 170) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 170)
        TutorialIsland.process(player)
    }
}

on_event(CookedShrimpEvent::class.java) {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 120) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 120)
        TutorialIsland.process(player)
    }
}

on_event(CreateBreadDoughEvent::class.java) {
    player.queue { itemMessageBox(item = Items.BREAD_DOUGH, message = "You make some dough.") }
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 160) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 160)
        TutorialIsland.process(player)
    }
}

on_event(FireStartedEvent::class.java) {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) <  90) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 90)
        TutorialIsland.process(player)
    }
}

on_event(ToggleRunEvent::class.java) {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) == 200) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 210)
        TutorialIsland.process(player)
    }
}

on_event(TreeCutDownEvent::class.java) {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 80) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 80)
        TutorialIsland.process(player)
    }
}

on_event(MineTinEvent::class.java) {
    player.queue { itemMessageBox("You manage to mine some tin.", item = Items.TIN_ORE, amountOrZoom = 400) }
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 310) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 310)
        TutorialIsland.process(player)
    }
}

on_event(MineCopperEvent::class.java) {
    player.queue { itemMessageBox("You manage to mine some copper.", item = Items.COPPER_ORE, amountOrZoom = 400) }
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 320) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 320)
        TutorialIsland.process(player)
    }
}

on_event(CreateBronzeDaggerEvent::class.java) {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 360) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 360)
        TutorialIsland.process(player)
    }
}

on_event(OpenSmithingInterfaceEvent::class.java) {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 350) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 350)
        TutorialIsland.process(player)
    }
}

fun listenTabClick(dest: InterfaceDestination, plugin: Plugin.() -> Unit) {
    val parents = intArrayOf(548, 161, 164)

    parents.forEach { parent ->
        val component = when(parent) {
            548 -> TutorialIsland.TAB_CHILD_IDS[dest]!![0]
            161 -> TutorialIsland.TAB_CHILD_IDS[dest]!![1]
            164 -> TutorialIsland.TAB_CHILD_IDS[dest]!![2]
            else -> -1
        }

        on_button(interfaceId = parent, component = component) {
            plugin()
        }
    }
}