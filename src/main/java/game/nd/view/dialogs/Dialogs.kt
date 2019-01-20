package game.nd.view.dialogs

import game.nd.world.GameContext
import org.hexworks.zircon.api.screen.Screen

fun openHelpDialog(screen: Screen) {
    screen.openModal(HelpDialog(screen))
}

fun openInventoryDialog(context: GameContext) {
    context.screen.openModal(InventoryDialog(context))
}