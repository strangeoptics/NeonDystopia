package game.nd.system

import game.nd.attribute.type.inventory
import game.nd.command.DropItem
import game.nd.extentions.GameCommand
import game.nd.extentions.responseWhenCommandIs
import game.nd.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object ItemDropper : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs<DropItem> { (context, itemHolder, item, position) ->
        if (itemHolder.inventory.removeItem(item)) {
            context.world.addEntity(item, position)
        }
        Consumed
    }
}
