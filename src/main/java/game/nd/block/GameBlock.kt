package game.nd.block


import game.nd.builder.GameTileRepository
import game.nd.extentions.*
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.data.base.BlockBase
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.graphics.Symbols

class GameBlock() : BlockBase<Tile>() {
    override fun setSide(side: BlockSide, tile: Tile) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createCopy(): Block<Tile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var pos: Position3D = Position3D.create(0,0,0)
    var defaultTile: Tile = Tile.defaultTile()
    private val currentEntities: MutableList<GameEntity<EntityType>> = mutableListOf()
    var blockingByTile: Boolean = false

    override val layers get() = mutableListOf(defaultTile, currentEntities.map {
        if(it.isMultitile) it.getMultitile(pos.to2DPosition().minus(it.position.to2DPosition()))
        else it.tile }.lastOrNull() ?: GameTileRepository.EMPTY)
    //override val layers get() = mutableListOf(defaultTile, Tiles.empty())

    override fun fetchSide(side: BlockSide): Tile {
        return Tile.defaultTile()
    }

    val entities: Iterable<GameEntity<EntityType>>
        get() = currentEntities.toList()

    fun addEntity(entity: GameEntity<EntityType>) {
        currentEntities.add(entity)
    }

    fun removeEntity(entity: GameEntity<EntityType>) : Boolean {
        return currentEntities.remove(entity)
    }

    val isOccupied: Boolean
        get() = currentEntities.any { it.occupiesBlock }

    val hasStairsDown: Boolean
        get() = currentEntities.any { it.isStairsDown }

    val hasStairsUp: Boolean
        get() = currentEntities.any { it.isStairsUp }

    fun withTopNonPlayerEntity(fn: (GameEntity<EntityType>) -> Unit) {
        currentEntities.firstOrNull { it.isPlayer.not() }?.let { entity ->
            fn(entity)
        }
    }

    companion object {

        fun create(): GameBlock = GameBlock()

        fun create(tile: CharacterTile) : GameBlock {
            var gb = GameBlock()
            gb.defaultTile = tile
            if(tile.character in charArrayOf(Symbols.DOUBLE_LINE_VERTICAL, Symbols.DOUBLE_LINE_HORIZONTAL,
                            Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER, Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER,
                            Symbols.DOUBLE_LINE_TOP_LEFT_CORNER, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER,
                            Symbols.SINGLE_LINE_HORIZONTAL, Symbols.SINGLE_LINE_VERTICAL)) {
                gb.blockingByTile = true
            }
            return gb
        }

        fun createWith(entity: GameEntity<EntityType>) = GameBlock().also {
            it.addEntity(entity)
        }

    }
}