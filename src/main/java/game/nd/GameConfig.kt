package game.nd

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.application.AppConfig
import java.awt.Toolkit

object GameConfig {

    private val SCREEN_SIZE = Toolkit.getDefaultToolkit().screenSize
    private const val SCREEN_SIZE_PERCENT = 0.6
    private const val FULL_SCREEN = false
    val TILESET = CP437TilesetResources.rexPaint12x12()
    val THEME = ColorThemes.zenburnVanilla()
    val WINDOW_WIDTH = SCREEN_SIZE.width.div(TILESET.width).times(SCREEN_SIZE_PERCENT).toInt()
    val WINDOW_HEIGHT = SCREEN_SIZE.height.div(TILESET.height).times(SCREEN_SIZE_PERCENT).toInt()
    const val SIDEBAR_WIDTH = 20

    @Suppress("ConstantConditionIf")
    fun buildAppConfig(): AppConfig {
        val config = AppConfigs.newConfig()
                .enableBetaFeatures()
                .withDefaultTileset(TILESET)

        if (FULL_SCREEN) {
            config.fullScreen()
        } else {
            config.withSize(Sizes.create(WINDOW_WIDTH, WINDOW_HEIGHT))
        }
        return config.build()
    }
}