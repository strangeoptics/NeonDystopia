package game.nd.world

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import game.nd.block.GameBlock
import game.nd.builder.GameTileRepository
import game.nd.component.MovementComponent
import game.nd.component.PlayerComponent
import game.nd.component.PositionComponent
import game.nd.component.RandomWalkComponent
import game.nd.entity.GameEntity
import game.nd.system.MovementSystem
import game.nd.system.PlayerInputSystem
import game.nd.system.RandomWalkSystem
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea

class WorldImpl(visibleSize: Size3D, actualSize: Size3D) : GameArea<Tile, GameBlock> by GameAreaBuilder.newBuilder<Tile, GameBlock>()
        .withVisibleSize(visibleSize)
        .withActualSize(actualSize)
        .withDefaultBlock(GameBlock.create())
        .withLayersPerBlock(2)
        .build() {

    var engine = Engine()
    var playerInputSystem = PlayerInputSystem()
    var player = GameEntity(GameTileRepository.PLAYER, Position3D.create(30, 30, 0))
    var enemy0 = GameEntity(GameTileRepository.ZOMBIE, Position3D.create(30, 40, 0))


    init {
        var movementSystem = MovementSystem(this)

        engine.addSystem(playerInputSystem)
        engine.addSystem(RandomWalkSystem(this))
        engine.addSystem(movementSystem)


        player.add(PlayerComponent())
        var posComp = PositionComponent()
        posComp.position = Position3D.create(30, 30, 0)
        player.add(posComp)
        player.add(MovementComponent())

        enemy0.add(PositionComponent(Position3D.create(30,40,0)))
        enemy0.add(MovementComponent())
        enemy0.add(RandomWalkComponent())

        engine.addEntity(player)
        engine.addEntity(enemy0)




    }

    fun moveEntity(entity: GameEntity, from: Position3D, to: Position3D): Boolean {
        var targetBlock = fetchBlockAt(to).get()
        if(targetBlock.blockingByTile) return false

        var block = fetchBlockAt(from)
        block.get().removeEntity(entity)

        fetchBlockAt(to).get().addEntity(entity)
        return true
    }

    fun moveEntity(entity: GameEntity, position: Position3D): Boolean {
        var targetBlock = fetchBlockAt(position).get()
        if(targetBlock.blockingByTile) return false

        var block = fetchBlockAt(entity.position)
        block.get().removeEntity(entity)

        entity.position = position
        fetchBlockAt(position).get().addEntity(entity)
        return true
    }

}