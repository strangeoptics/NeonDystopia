package game.nd.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import game.nd.component.Mappers
import game.nd.component.MovementComponent
import game.nd.component.PositionComponent
import game.nd.entity.GameEntity
import game.nd.world.WorldImpl


class MovementSystem(val gameArea: WorldImpl) : EntitySystem() {

    override fun update(deltaTime: Float) {
        var entities = engine.getEntitiesFor(FAMILY)
        for(entity in entities) {
            var mc = Mappers.movements.get(entity)
            if(mc.move) {
                var pc = Mappers.positions.get(entity)
                println("update")
                if(entity is GameEntity) {
                    if(gameArea.moveEntity(entity, pc.position, pc.position.plus(mc.positionDelta))) {
                        pc.position = pc.position.withRelative(mc.positionDelta)
                    }
                }
                mc.move = false
            }
        }
    }

    companion object {
        private val FAMILY = Family.all(PositionComponent::class.java, MovementComponent::class.java).get()
    }
}