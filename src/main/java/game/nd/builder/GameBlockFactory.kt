package game.nd.builder

import game.nd.block.GameBlock
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.Symbols

object GameBlockFactory {

    fun stairsDown() = GameBlock.createWith(EntityFactory.newStairsDown())

    fun stairsUp() = GameBlock.createWith(EntityFactory.newStairsUp())


    fun wall(tile: CharacterTile) = GameBlock.createWith(EntityFactory.newWall(tile))

    fun window(tile: CharacterTile) = GameBlock.createWith(EntityFactory.newWindow(tile))

    fun door(tile: CharacterTile) = if(tile.character == '+') GameBlock.createWith(EntityFactory.newDoorOpened(tile)) else GameBlock.createWith(EntityFactory.newDoor(tile))

    fun bed(tile: CharacterTile) = GameBlock.createWith(EntityFactory.newBed(tile))

    fun counter(tile: CharacterTile) = GameBlock.createWith(EntityFactory.newCounter(tile))

    fun fromCapsulHotel(tile: CharacterTile) : GameBlock {
        return when(tile.character) {
            Symbols.WHITE_CIRCLE, '+' -> door(tile)
            Symbols.BLOCK_SOLID, Symbols.RIGHT_HALF_BLOCK -> bed(tile)
            Symbols.LEFT_HALF_BLOCK, Symbols.UPPER_HALF_BLOCK -> counter(tile)
            '<' -> stairsUp()
            else -> fromDownTownMap(tile)
        }
    }

    fun fromDownTownMap(tile: CharacterTile) : GameBlock {
        if(wallChars.contains(tile.character)) {
            return wall(tile)
        } else
        if(windowChars.contains(tile.character)) {
            return window(tile)
        }

        return GameBlock.create(tile)
    }

    val wallChars = charArrayOf(Symbols.DOUBLE_LINE_VERTICAL, Symbols.DOUBLE_LINE_HORIZONTAL,
            Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER, Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER,
            Symbols.DOUBLE_LINE_TOP_LEFT_CORNER, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER,
            Symbols.DOUBLE_LINE_T_DOWN, Symbols.DOUBLE_LINE_T_LEFT, Symbols.DOUBLE_LINE_T_RIGHT, Symbols.DOUBLE_LINE_T_UP)
    val windowChars = charArrayOf(Symbols.SINGLE_LINE_HORIZONTAL, Symbols.SINGLE_LINE_VERTICAL)
}