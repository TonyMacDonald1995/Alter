package org.alter.plugins.content.area.tutorial_island.events

import org.alter.api.cfg.Varp
import org.alter.api.ext.setVarp
import org.alter.game.event.Event
import org.alter.game.model.entity.Player
import org.alter.plugins.content.area.tutorial_island.TutorialIsland

object TreeCutDownEvent : Event {
    fun execute(player: Player) {
        player.setVarp(Varp.TUTORIAL_ISLAND_PROGRESSION, 80)
        TutorialIsland.process(player)
    }
}