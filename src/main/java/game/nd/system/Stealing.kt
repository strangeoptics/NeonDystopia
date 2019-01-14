package game.nd.system

import game.nd.command.MoveTo
import game.nd.command.Steal
import game.nd.extentions.GameCommand
import game.nd.extentions.hasCryptos
import game.nd.extentions.whenCommandIs
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object Stealing : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<Steal> { (context, entity, trgEntity) ->
        println("Stealing")
        if(trgEntity.hasCryptos) {
            println("has cryptos")
        }
    }
}
