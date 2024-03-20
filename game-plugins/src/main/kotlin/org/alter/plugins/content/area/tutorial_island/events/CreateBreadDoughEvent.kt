package org.alter.plugins.content.area.tutorial_island.events

import org.alter.api.cfg.Items
import org.alter.api.cfg.Varp
import org.alter.api.ext.itemMessageBox
import org.alter.api.ext.setVarp
import org.alter.game.event.Event
import org.alter.game.model.entity.Player
import org.alter.plugins.content.area.tutorial_island.TutorialIsland

object CreateBreadDoughEvent : Event {
    fun execute(player: Player) {
        player.queue { itemMessageBox(item = Items.BREAD_DOUGH, message = "You make some dough.") }
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 160)
        TutorialIsland.process(player)
    }
}