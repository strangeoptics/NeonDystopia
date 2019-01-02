package game.nd.builder

import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {

    val EMPTY: CharacterTile = Tiles.empty()

    private val DEFAULT = Tiles.newBuilder()
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val FLOOR = Tiles.newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(ANSITileColor.YELLOW)
            .buildCharacterTile()


    val UNREVEALED = DEFAULT
            .withCharacter(' ')
            .withBackgroundColor(GameColors.UNREVEALED_COLOR)

    val OUT_OF_LOS = DEFAULT
            .withCharacter(' ')
            .withBackgroundColor(GameColors.OUT_OF_LOS_COLOR)

    val PLAYER = DEFAULT
            .withCharacter('@')
            .withForegroundColor(GameColors.ACCENT_COLOR)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)

    val CITIZEN = DEFAULT
            .withCharacter('c')
            .withForegroundColor(GameColors.FUNGUS_COLOR)


    val STAIRS_UP = DEFAULT
            .withCharacter('<')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    val STAIRS_DOWN = DEFAULT
            .withCharacter('>')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    val CORPSE = DEFAULT
            .withCharacter('%')
            .withForegroundColor(GameColors.ACCENT_COLOR)



    val WALL_VARIATION_D_V = Tiles.newBuilder()
            .withCharacter('║')
            .withForegroundColor(GameColors.WALL_FOREGROUND)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()


}

