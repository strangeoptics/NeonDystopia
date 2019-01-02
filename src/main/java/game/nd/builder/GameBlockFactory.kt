package game.nd.builder

import game.nd.block.GameBlock
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.Symbols

object GameBlockFactory {

    fun stairsDown() = GameBlock.createWith(EntityFactory.newStairsDown())

    fun stairsUp() = GameBlock.createWith(EntityFactory.newStairsUp())


    fun wall(tile: CharacterTile) = GameBlock.createWith(EntityFactory.newWall(tile))

    fun fromDownTownMap(tile: CharacterTile) : GameBlock {
        if(wallChars.contains(tile.character)) {
            return wall(tile)
        }
        return GameBlock.create(tile)
    }

    val wallChars = charArrayOf(Symbols.DOUBLE_LINE_VERTICAL, Symbols.DOUBLE_LINE_HORIZONTAL,
            Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER, Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER,
            Symbols.DOUBLE_LINE_TOP_LEFT_CORNER, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER,
            Symbols.SINGLE_LINE_HORIZONTAL, Symbols.SINGLE_LINE_VERTICAL)
}