package game.nd.view

import game.nd.GameConfig
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.grid.TileGrid

abstract class BaseVieww(tileGrid: TileGrid) : View {

    final override val screen = Screens.createScreenFor(tileGrid)
    private val colorTheme = GameConfig.THEME

    final override fun dock() {
        screen.applyColorTheme(colorTheme)
        screen.display()
    }
}