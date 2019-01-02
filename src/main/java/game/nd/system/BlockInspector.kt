package game.nd.system

import game.nd.command.LookAt
import game.nd.extentions.GameCommand
import game.nd.extentions.isPlayer
import game.nd.extentions.logGameEvent
import game.nd.extentions.whenCommandIs
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object BlockInspector : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<LookAt> {
        (context, source, position) ->

        if (source.isPlayer) {
            context.world.withBlockAt(position) { block ->
                block.withTopNonPlayerEntity { entity ->
                    logGameEvent("You see: ${entity.name}.")
                }
            }
        }
    }
}
