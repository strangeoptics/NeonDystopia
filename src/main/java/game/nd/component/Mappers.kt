package game.nd.component

import com.badlogic.ashley.core.ComponentMapper

object Mappers {

    val positions = ComponentMapper.getFor(PositionComponent::class.java)
    val movements = ComponentMapper.getFor(MovementComponent::class.java)
    val randomWalks = ComponentMapper.getFor(RandomWalkComponent::class.java)
}