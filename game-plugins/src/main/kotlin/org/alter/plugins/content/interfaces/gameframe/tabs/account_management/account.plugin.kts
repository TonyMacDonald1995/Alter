package org.alter.plugins.content.interfaces.gameframe.tabs.account_management

/**
 * @author Fritz <frikkipafi@gmail.com>
 */
/**
 * Account Tab Buttons
 */

on_button(AccountTab.BUTTON_ID, AccountTab.Buttons.NAME_CHANGER.buttonId) {
    player.openInterface(interfaceId = 589, dest = InterfaceDestination.TAB_AREA)
    player.setComponentText(interfaceId = 589, component = 6, text = "Next free change:")
    player.setComponentText(interfaceId = 589, component = 7, text = "Now!") // TODO Make this a method to pull last updated date from your database, return that date, or "Now!"
    player.setInterfaceEvents(interfaceId = 589, component = 18, range = 0..9, setting = 0)
    player.setVarbit(5605, 1)
}

AccountTab.Buttons.values().forEach {
    if (it == AccountTab.Buttons.NAME_CHANGER)
        return@forEach
    on_button(AccountTab.BUTTON_ID, it.buttonId) {
        player.message("Button: [${it.name} : ${it.buttonId}]")
    }
}



listOf(6, 11, 16).forEachIndexed { index, it ->
    on_button(109 , it) {
        player.setVarbit(10060, index)
    }
}