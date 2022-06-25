import gg.rsmod.game.model.attr.INTERACTING_SLOT_ATTR
import gg.rsmod.game.model.interf.DisplayMode
import gg.rsmod.plugins.content.inter.options.OptionsTab

fun bind_setting(child: Int, plugin: Plugin.() -> Unit) {
    on_button(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = child) {
        plugin(this)
    }
}


on_login {
    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 6, range = 1..4, setting = 2) // Player option priority
    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 7, range = 1..4, setting = 2) // Npc option priority
    //player.setInterfaceEvents(interfaceId = OPTIONS_INTERFACE_ID, component = 40, range = 0..4, setting = 2)
    //player.setInterfaceEvents(interfaceId = OPTIONS_INTERFACE_ID, component = 42, range = 0..4, setting = 2)
    //player.setInterfaceEvents(interfaceId = OPTIONS_INTERFACE_ID, component = 44, range = 0..4, setting = 2)
    //player.setInterfaceEvents(interfaceId = OPTIONS_INTERFACE_ID, component = 35, range = 1..4, setting = 2)
    //player.setInterfaceEvents(interfaceId = OPTIONS_INTERFACE_ID, component = 36, range = 1..4, setting = 2)
    //player.setInterfaceEvents(interfaceId = OPTIONS_INTERFACE_ID, component = 25, range = 1..5, setting = 2)
    //player.setInterfaceEvents(interfaceId = OPTIONS_INTERFACE_ID, component = 115, range = 0..3, setting = 2)

    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 55, range = 0..21, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 84, range = 1..3, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 69, range = 0..21, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 82, range = 1..4, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 83, range = 1..5, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 81, range = 1..5, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 41, range = 0..21, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.OPTIONS_INTERFACE_ID, component = 23, range =  0..21, setting = 2)
}
bind_setting(child = 84) {
    //val slot = player.getInteractingSlot()
    val slot = player.attr[INTERACTING_SLOT_ATTR]!!
    val mode = when (slot) {
        2 -> {
            player.setVarbit(OSRSGameframe.SIDESTONES_ARRAGEMENT_VARBIT, 0)
            DisplayMode.RESIZABLE_NORMAL
        }
        3 -> {
            player.setVarbit(OSRSGameframe.SIDESTONES_ARRAGEMENT_VARBIT, 1)
            DisplayMode.RESIZABLE_LIST
        }
        else -> DisplayMode.FIXED
    }
    if(!(mode.isResizable() && player.interfaces.displayMode.isResizable()))
    player.runClientScript(3998, slot-1)
    player.toggleDisplayInterface(mode)
}
/**
 * Toggle run mode
 */
bind_setting(child = OptionsTab.RUN_MODE_BUTTON_ID) {
    player.message("Run toggle")
    player.toggleVarp(OSRSGameframe.RUN_MODE_VARP)
}

/**
 * Accept aid toggle.
 */
bind_setting(child = OptionsTab.ACCEPT_AID_BUTTON_ID) {
    player.message("Aid toggle")
    player.toggleVarbit(OSRSGameframe.ACCEPT_AID_VARBIT)
}

/**
 * All settings button
 */
bind_setting(child = OptionsTab.ALL_SETTINGS_BUTTON_ID) {
    if (!player.lock.canInterfaceInteract()) {
        return@bind_setting
    }

    player.message("Opening All settings")

    val main_parent = getDisplayComponentId(player.interfaces.displayMode)
    val main_child = getChildId(InterfaceDestination.MAIN_SCREEN, player.interfaces.displayMode)
    val world_child = getChildId(InterfaceDestination.WORLD_MAP, player.interfaces.displayMode)

    if(player.interfaces.isOccupied(main_parent, main_child) || player.interfaces.isOccupied(main_parent, world_child)){
        player.message("Please finish what you are doing before opening the settings.")
        return@bind_setting
    }


    player.openInterface(interfaceId = OptionsTab.ALL_SETTINGS_INTERFACE_ID, parent = main_parent, child = world_child)
    player.setInterfaceEvents(interfaceId = OptionsTab.ALL_SETTINGS_INTERFACE_ID, component = 21, range = 0..147, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.ALL_SETTINGS_INTERFACE_ID, component = 23, range = 0..7, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.ALL_SETTINGS_INTERFACE_ID, component = 19, range = 0..264, setting = 2)
    player.setInterfaceEvents(interfaceId = OptionsTab.ALL_SETTINGS_INTERFACE_ID, component = 28, range = 0..41, setting = 2)
}