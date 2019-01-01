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

    val BAT = DEFAULT
            .withCharacter('b')
            .withForegroundColor(GameColors.BAT_COLOR)

    val ZOMBIE = DEFAULT
            .withCharacter('z')
            .withForegroundColor(GameColors.ZOMBIE_COLOR)

    val STAIRS_UP = DEFAULT
            .withCharacter('<')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    val STAIRS_DOWN = DEFAULT
            .withCharacter('>')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    val CORPSE = DEFAULT
            .withCharacter('%')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    // items

    val ROCK = DEFAULT
            .withCharacter(',')
            .withForegroundColor(GameColors.ROCK_COLOR)

    val MUSHROOM = DEFAULT
            .withCharacter('m')
            .withForegroundColor(GameColors.MUSHROOM_COLOR)

    val BAT_MEAT = DEFAULT
            .withCharacter('m')
            .withForegroundColor(GameColors.BAT_MEAT_COLOR)

    val DAGGER = DEFAULT
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.WHITE)

    val SWORD = DEFAULT
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.BRIGHT_WHITE)

    val STAFF = DEFAULT
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.YELLOW)

    val LIGHT_ARMOR = DEFAULT
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.GREEN)

    val MEDIUM_ARMOR = DEFAULT
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.WHITE)

    val HEAVY_ARMOR = DEFAULT
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.BRIGHT_WHITE)


}

