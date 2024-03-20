package org.alter.plugins.content.area.tutorial_island.events

import org.alter.game.event.Event
import org.alter.game.model.entity.Player
import org.alter.plugins.content.area.tutorial_island.staticDialog

object ChopTreeEvent : Event {
    fun execute(player: Player) {
        player.staticDialog("<col=0000ff>Please wait</col><br>Your character is now attempting to cut down the tree. Sit back for a moment while he does all the hard work.")
    }
}