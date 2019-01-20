package game.nd.system

import game.nd.attribute.StartStop
import game.nd.command.MoveTo
import game.nd.extentions.GameEntity
import game.nd.extentions.position
import game.nd.extentions.startPosition
import game.nd.extentions.stopPosition
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.impl.Position3D

object CarBehavior : BaseBehavior<GameContext>() {

    val directions = listOf<Position3D>(
            Position3D.create(-1,1,0), Position3D.create(0,1,0), Position3D.create(1,1,0),
            Position3D.create(-1,0,0), Position3D.create(0,0,0), Position3D.create(1,0,0),
            Position3D.create(-1,-1,0), Position3D.create(0,-1,0), Position3D.create(1,-1,0))

    override fun update(entity: GameEntity<EntityType>, context: GameContext): Boolean {
        val (world, screen, input, player) = context

        val startStop = entity.attribute(StartStop::class)
        val (start, stop, direction, speed) = startStop.get()

        var newPos = entity.position.withRelative(Position3D.create(direction.x * speed, direction.y * speed, direction.z))

        if(direction.y == 1 && entity.position.y > entity.stopPosition.y) {
            newPos = entity.startPosition
        } else
        if(direction.y == -1 && entity.position.y < entity.stopPosition.y) {
            newPos = entity.startPosition
        }
        entity.executeCommand(MoveTo(context, entity, newPos))

        return true
    }
}