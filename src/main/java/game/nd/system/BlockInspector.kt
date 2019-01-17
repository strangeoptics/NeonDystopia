package game.nd.system

import game.nd.attribute.type.Door
import game.nd.command.LookAt
import game.nd.extentions.*
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object BlockInspector : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<LookAt> {
        (context, source, position) ->

        if (source.isPlayer) {
            context.world.withBlockAt(position) { block ->
                block.withTopNonPlayerEntity { entity ->
                    when(entity.type) {
                        Door -> logGameEvent("You see: A ${if(entity.occupiesBlock) "closed" else ""} door" )
                        else -> logGameEvent("You see: ${entity.name}.")
                    }
                }
            }
        }
    }
}
