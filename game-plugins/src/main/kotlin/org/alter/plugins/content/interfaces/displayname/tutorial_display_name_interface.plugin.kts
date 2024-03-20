package org.alter.plugins.content.interfaces.displayname

import org.alter.game.model.attr.DISPLAY_NAME_SET_ATTR
import org.alter.game.model.attr.RETURN_VALUE_ATTR
import org.alter.game.service.serializer.PlayerSerializerService
import org.alter.plugins.content.area.tutorial_island.TutorialIsland
import org.alter.plugins.service.sql.SQLService

//Button Name Box
on_button(DISPLAY_NAME_INTERFACE_ID, 7) {
    player.setVarbit(Varbit.TUTORIAL_DISPLAY_NAME_STATUS, 1)
    player.runClientScript(4139, 36569106, 36569107, 36569100, 36569096, 36569095, 1)
}

//Button Set Name
on_button(DISPLAY_NAME_INTERFACE_ID, 19) {//Button Set Name
    val name: String = player.attr[RETURN_VALUE_ATTR] as? String ?: return@on_button
    player.setComponentText(DISPLAY_NAME_INTERFACE_ID, 13, "Requesting...")
    player.setVarbit(Varbit.TUTORIAL_DISPLAY_NAME_STATUS, 5)
    player.displayname = name
    player.setVarbit(5607, 1)
    player.playJingle(2655)
    player.runClientScript(4145)
    player.setVarbit(8119, 1)
    player.closeInterface(DISPLAY_NAME_INTERFACE_ID)
    player.runClientScript(2524, -1, -1)
    player.attr[DISPLAY_NAME_SET_ATTR] = true
    player.unlock()
    TutorialIsland.process(player)
}

on_return_value {
    val name: String = player.attr[RETURN_VALUE_ATTR] as? String ?: return@on_return_value
    player.setVarbit(Varbit.TUTORIAL_DISPLAY_NAME_STATUS, 2)
    player.runClientScript(4144, 36569106, 36569107, 36569100, 36569096, 36569095, name)
    val sqlService = player.world.getService(PlayerSerializerService::class.java, true) as SQLService
    if (sqlService.checkDisplayName(name) && name.length >= 3) {
        player.setComponentText(DISPLAY_NAME_INTERFACE_ID, 13, "Sorry, the display name <col=ffffff>$name</col> is <col=ff0000>not available</col>.")
        player.setComponentHidden(DISPLAY_NAME_INTERFACE_ID, 14, false)
        player.setVarbit(Varbit.TUTORIAL_DISPLAY_NAME_STATUS, 1)
        player.runClientScript(4139, 36569106, 36569107, 36569100, 36569096, 36569095, 1)
    } else {
        player.runClientScript(4144, 36569106, 36569107, 36569100, 36569096, 36569095, name)
        player.setComponentText(DISPLAY_NAME_INTERFACE_ID, 13, "Great! The display name <col=ffffff>$name</col> is <col=00ff00>available</col>!<br>You may set this name now, or enter another to look up.")
        player.setComponentHidden(DISPLAY_NAME_INTERFACE_ID, 14, true)
        player.setVarbit(Varbit.TUTORIAL_DISPLAY_NAME_STATUS, 4)
    }
}