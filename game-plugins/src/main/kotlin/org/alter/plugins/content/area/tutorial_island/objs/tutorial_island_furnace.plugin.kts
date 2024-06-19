package org.alter.plugins.content.area.tutorial_island.objs

import org.alter.plugins.content.area.tutorial_island.TutorialIsland

on_obj_option(Objs.FURNACE_10082, "use") {
    if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 320) {
        //TODO before smelting message
        return@on_obj_option
    }

    if (!player.inventory.contains(Items.COPPER_ORE) || !player.inventory.contains(Items.TIN_ORE)) {
        //TODO no ore message
        return@on_obj_option
    }

    player.queue {
        player.lock = LockState.DELAY_ACTIONS
        player.animate(Animation.FURNACE_SMELT)
        player.playSound(Sound.FURNACE)
        messageBox("You smelt the copper and tin together in the furnace.")
        wait(2)
        player.animate(Animation.FURNACE_SMELT)
        player.inventory.remove(Items.COPPER_ORE, 1, true)
        player.inventory.remove(Items.TIN_ORE, 1, true)
        player.inventory.add(Items.BRONZE_BAR)
        player.addXp(Skills.SMITHING, 6.2)
        messageBox("You retrieve a bar of bronze.")
        player.unlock()

        if (player.getVarp(Varp.TUTORIAL_ISLAND_PROGRESSION) < 330) {
            player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 330)
            TutorialIsland.process(player)
        }
    }
}