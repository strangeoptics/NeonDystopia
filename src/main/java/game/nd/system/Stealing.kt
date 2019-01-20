package game.nd.system

import game.nd.command.Steal
import game.nd.extentions.GameCommand
import game.nd.extentions.hasCryptos
import game.nd.extentions.responseWhenCommandIs
import game.nd.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object Stealing : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs<Steal> { (context, entity, trgEntity) ->
        println("Stealing")
        if(trgEntity.hasCryptos) {
            println("has cryptos")
        }
        Consumed
    }
}
