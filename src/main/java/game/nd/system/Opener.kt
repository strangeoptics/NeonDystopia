package game.nd.system

import game.nd.attribute.BlockOccupier
import game.nd.attribute.Openable
import game.nd.attribute.type.Door
import game.nd.command.Open
import game.nd.command.Steal
import game.nd.extentions.*
import game.nd.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.extensions.map

object Opener : BaseFacet<GameContext>() {
    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs<Open> { (context, entity, trgEntity) ->
        println("Opening")
        trgEntity.attribute(Openable::class).map {
            println("openable")
            trgEntity.attribute(BlockOccupier::class).map {
                //trgEntity. remove BlockOccupier
            }
        }
        Consumed
    }
}