package game.nd.world

import game.nd.block.GameBlock
import game.nd.builder.EntityFactory
import game.nd.extentions.GameEntity
import game.nd.extentions.cryptoCounter
import game.nd.extentions.position
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEngine
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.screen.Screen

class WorldImpl(visibleSize: Size3D, actualSize: Size3D) : GameArea<Tile, GameBlock> by GameAreaBuilder.newBuilder<Tile, GameBlock>()
        .withVisibleSize(visibleSize)
        .withActualSize(actualSize)
        .withDefaultBlock(GameBlock.create())
        .withLayersPerBlock(2)
        .build() {

    val engine = newEngine<GameContext>()
    val player = EntityFactory.newPlayer()

    init {
        player.position = Position3D.create(35,30,0)
        player.cryptoCounter.cryptosCount = 5
        engine.addEntity(player)

    }

    fun moveEntity(entity: GameEntity<EntityType>, from: Position3D, to: Position3D): Boolean {

        if(fetchBlockAt(to).isPresent && fetchBlockAt(from).isPresent) {
            var targetBlock = fetchBlockAt(to).get()
            if (targetBlock.blockingByTile) return false

            var block = fetchBlockAt(from)
            block.get().removeEntity(entity)

            fetchBlockAt(to).get().addEntity(entity)

            return true
        }
        return false
    }

    fun update(screen: Screen, input: Input) {
        engine.update(GameContext(
                world = this,
                screen = screen,
                input = input,
                player = player))

    }

    fun whenHasBlockAt(pos: Position3D, fn: (GameBlock) -> Unit) {
        fetchBlockAt(pos).map(fn)
    }

    fun withBlockAt(position: Position3D, fn: (GameBlock) -> Unit) {
        fetchBlockAt(position).map(fn)
    }
}