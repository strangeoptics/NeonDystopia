package game.nd.block

import game.nd.entity.Entity
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BlockBase

class GameBlock : BlockBase<Tile>() {

    var defaultTile: Tile = Tile.defaultTile()
    private val currentEntities: MutableList<Entity> = mutableListOf()

    //override val layers get() = mutableListOf(defaultTile, currentEntities.map { it.tile }.lastOrNull() ?: Tile.defaultTile())
    override val layers get() = mutableListOf(defaultTile, Tiles.empty())

    override fun fetchSide(side: BlockSide): Tile {
        return Tile.defaultTile()
    }

    fun addEntity(entity: Entity) {
        currentEntities.add(entity)
    }

    companion object {

        fun create(): GameBlock = GameBlock()

        fun createWith(entity: Entity) = GameBlock().also {
            it.addEntity(entity)
        }

    }
}