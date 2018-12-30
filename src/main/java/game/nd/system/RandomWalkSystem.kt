package game.nd.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import game.nd.component.Mappers
import game.nd.component.PositionComponent
import game.nd.component.RandomWalkComponent
import game.nd.world.WorldImpl
import org.hexworks.zircon.api.data.impl.Position3D
import kotlin.random.Random

class RandomWalkSystem(val gameArea: WorldImpl) : EntitySystem() {

    override fun update(deltaTime: Float) {
        var entities = engine.getEntitiesFor(RandomWalkSystem.FAMILY)
        for(entity in entities) {
            var rw = Mappers.randomWalks.get(entity)
            var pc = Mappers.positions.get(entity)
            var mc = Mappers.movements.get(entity)

            mc.positionDelta(directions.get(Random.nextInt(0, 8)))
            println("RandomWalk: update")
        }
    }

    companion object {
        private val FAMILY = Family.all(PositionComponent::class.java, RandomWalkComponent::class.java).get()

        val directions = listOf<Position3D>(
                Position3D.create(-1,1,0), Position3D.create(0,1,0), Position3D.create(1,1,0),
                Position3D.create(-1,0,0), Position3D.create(0,0,0), Position3D.create(1,0,0),
                Position3D.create(-1,-1,0), Position3D.create(0,-1,0), Position3D.create(1,-1,0))
    }
}