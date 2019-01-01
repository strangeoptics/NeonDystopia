package game.nd.world

import com.badlogic.ashley.core.Engine
import game.nd.block.GameBlock
import game.nd.builder.EntityFactory
import game.nd.builder.GameTileRepository
import game.nd.component.MovementComponent
import game.nd.component.PlayerComponent
import game.nd.component.PositionComponent
import game.nd.component.RandomWalkComponent
import game.nd.extentions.GameEntity
import game.nd.extentions.position
import game.nd.system.PlayerInputSystem
import game.nd.system.RandomWalkSystem
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEngine
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
        engine.addEntity(player)
    }

    fun moveEntity(entity: GameEntity<EntityType>, from: Position3D, to: Position3D): Boolean {
        var targetBlock = fetchBlockAt(to).get()
        if(targetBlock.blockingByTile) return false

        var block = fetchBlockAt(from)
        block.get().removeEntity(entity)

        fetchBlockAt(to).get().addEntity(entity)
        return true
    }

    fun update(screen: Screen, input: Input) {
        engine.update(GameContext(
                world = this,
                screen = screen,
                input = input,
                player = player))

    }

}