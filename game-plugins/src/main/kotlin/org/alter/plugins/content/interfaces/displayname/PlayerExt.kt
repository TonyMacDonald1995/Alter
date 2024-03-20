package org.alter.plugins.content.interfaces.displayname

import org.alter.api.InterfaceDestination
import org.alter.api.cfg.Varbit
import org.alter.api.ext.DISPLAY_NAME_INTERFACE_ID
import org.alter.api.ext.openInterface
import org.alter.api.ext.runClientScript
import org.alter.api.ext.setVarbit
import org.alter.game.model.entity.Player

fun Player.openDisplayNameInterface() {
    openInterface(dest = InterfaceDestination.MAIN_SCREEN, interfaceId = DISPLAY_NAME_INTERFACE_ID)
    setVarbit(Varbit.TUTORIAL_DISPLAY_NAME_STATUS, 1)
    runClientScript(4139, 36569106, 36569107, 36569100, 36569096, 36569095, 1)
}