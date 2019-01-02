package game.nd.view.dialogs

import org.hexworks.zircon.api.screen.Screen

fun openHelpDialog(screen: Screen) {
    screen.openModal(HelpDialog(screen))
}