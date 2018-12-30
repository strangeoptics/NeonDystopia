package game.nd.block


import com.badlogic.ashley.core.Entity
import game.nd.builder.GameTileRepository
import game.nd.entity.GameEntity
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BlockBase
import org.hexworks.zircon.api.graphics.Symbols

class GameBlock : BlockBase<Tile>() {

    var defaultTile: Tile = Tile.defaultTile()
    private val currentEntities: MutableList<GameEntity> = mutableListOf()
    var blockingByTile: Boolean = false

    override val layers get() = mutableListOf(defaultTile, currentEntities.map { it.tile }.lastOrNull() ?: GameTileRepository.EMPTY)
    //override val layers get() = mutableListOf(defaultTile, Tiles.empty())

    override fun fetchSide(side: BlockSide): Tile {
        return Tile.defaultTile()
    }

    fun addEntity(entity: GameEntity) {
        currentEntities.add(entity)
    }

    fun removeEntity(entity: GameEntity) : Boolean {
        return currentEntities.remove(entity)
    }

    companion object {

        fun create(): GameBlock = GameBlock()

        fun create(tile: CharacterTile) : GameBlock {
            var gb = GameBlock()
            gb.defaultTile = tile
            if(tile.character in charArrayOf(Symbols.DOUBLE_LINE_VERTICAL, Symbols.DOUBLE_LINE_HORIZONTAL,
                            Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER, Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER,
                            Symbols.DOUBLE_LINE_TOP_LEFT_CORNER, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER)) {
                gb.blockingByTile = true
            }
            return gb
        }

        fun createWith(entity: GameEntity) = GameBlock().also {
            it.addEntity(entity)
        }

    }
}