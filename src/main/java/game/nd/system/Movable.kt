package game.nd.system

import game.nd.command.MoveCamera
import game.nd.command.MoveTo
import game.nd.extentions.GameCommand
import game.nd.extentions.logGameEvent
import game.nd.extentions.position
import game.nd.extentions.whenCommandIs
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object Movable : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<MoveTo> { (context, entity, position) ->

        context.world.whenHasBlockAt(position) { block ->
            if(block.isOccupied) {

            } else {
                if(context.world.moveEntity(entity, entity.position, position)) {
                    entity.position = position
                    entity.executeCommand(MoveCamera(context, entity))
                }
            }
        }
        /*if(context.world.moveEntity(entity, entity.position, position)) {
            entity.position = position

            //logGameEvent("You see: ${entity.name}.")
            entity.executeCommand(MoveCamera(context, entity))
        }*/
        /*val world = context.world
        world.whenHasBlockAt(position) { block ->
            if (block.isOccupied) {
                entity.tryActionsOn(context, block.occupier)
            } else {
                world.moveEntity(entity, position)
                entity.executeCommand(LookAt(context, entity, position))
            }
        }*/
    }
}