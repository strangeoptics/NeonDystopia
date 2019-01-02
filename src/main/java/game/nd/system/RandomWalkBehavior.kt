package game.nd.system

import com.badlogic.ashley.core.Family
import game.nd.command.MoveTo
import game.nd.component.PositionComponent
import game.nd.component.RandomWalkComponent
import game.nd.extentions.GameEntity
import game.nd.extentions.position
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.impl.Position3D
import kotlin.random.Random

object RandomWalkBehavior  : BaseBehavior<GameContext>() {

    private val FAMILY = Family.all(PositionComponent::class.java, RandomWalkComponent::class.java).get()

    val directions = listOf<Position3D>(
            Position3D.create(-1,1,0), Position3D.create(0,1,0), Position3D.create(1,1,0),
            Position3D.create(-1,0,0), Position3D.create(0,0,0), Position3D.create(1,0,0),
            Position3D.create(-1,-1,0), Position3D.create(0,-1,0), Position3D.create(1,-1,0))

    override fun update(entity: GameEntity<EntityType>, context: GameContext): Boolean {
        val (world, screen, input, player) = context

        var dir = directions[Random.nextInt(0, 9)]
        entity.executeCommand(MoveTo(context, entity, entity.position.withRelative(dir)))

        return true
    }

    /*override fun update(deltaTime: Float) {
        var entities = engine.getEntitiesFor(RandomWalkSystem.FAMILY)
        for(entity in entities) {
            var rw = Mappers.randomWalks.get(entity)
            var pc = Mappers.positions.get(entity)
            var mc = Mappers.movements.get(entity)

            mc.positionDelta(directions.get(Random.nextInt(0, 9)))
            println("RandomWalk: update")
        }
    }*/




}