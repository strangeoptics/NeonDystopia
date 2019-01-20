package game.nd.system

import game.nd.command.MoveCamera
import game.nd.command.MoveTo
import game.nd.extentions.*
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.Consumed

object Movable : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs<MoveTo> { (context, entity, position) ->

        if(entity.isMultitile) {
            context.world.moveEntityMultitile(entity, entity.position, position)
            Consumed
        } else {
            context.world.whenHasBlockAt(position) { block ->
                if (block.isOccupied) {

                } else {
                    if (context.world.moveEntity(entity, entity.position, position)) {
                        entity.position = position
                        entity.executeCommand(MoveCamera(context, entity))
                    }
                }
            }
        }
        Consumed
    }
}