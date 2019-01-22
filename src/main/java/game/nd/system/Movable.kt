package game.nd.system

import game.nd.attribute.PassingDescription
import game.nd.attribute.type.Door
import game.nd.command.LookAt
import game.nd.command.MoveCamera
import game.nd.command.MoveTo
import game.nd.extentions.*
import game.nd.util.Direction4
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.cobalt.datatypes.extensions.map

object Movable : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs<MoveTo> { (context, entity, position) ->

        val dir = Direction4.from(position, entity.position)
        if(entity.isMultitile) {
            context.world.moveEntityMultitile(entity, entity.position, position)
            Consumed
        } else {
            context.world.whenHasBlockAt(position) { block ->
                if (block.isOccupied) {

                } else {
                    if (context.world.moveEntity(entity, entity.position, position)) {
                        entity.position = position
                        if(entity.isPlayer) {
                            entity.executeCommand(MoveCamera(context, entity))
                            context.world.whenHasBlockAt(entity.position) {
                                it.withTopNonPlayerEntity {

                                    val passingDescription = it.attribute(PassingDescription::class)


                                    if(passingDescription.isPresent) {
                                        passingDescription.get().map.forEach { t, u -> if (t == dir) {
                                                logGameEvent(u)
                                            }
                                        }
                                    } else {
                                        entity.executeCommand(LookAt(context, entity, entity.position))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Consumed
    }
}