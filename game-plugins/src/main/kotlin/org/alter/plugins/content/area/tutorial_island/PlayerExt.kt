package org.alter.plugins.content.area.tutorial_island

import org.alter.api.ext.openInterface
import org.alter.api.ext.player
import org.alter.api.ext.runClientScript
import org.alter.game.model.entity.Player
import org.alter.game.model.queue.TaskPriority


/**
 * Send a dialog without "Click here to continue"
 *
 * [overlay] determines if the dialog replaces the chatbox until closed with [closeInterface()]
 */
fun Player.staticDialog(message: String, overlay: Boolean = true) {
    this.queue(TaskPriority.WEAK) {
        this.player.openInterface(parent = 162, child = if(overlay) 558 else 559, interfaceId = 263, isModal = false) //childId = 559?
        this.player.runClientScript(1974, message)
    }
}