package game.nd.system

import game.nd.attribute.type.inventory
import game.nd.command.PickItemUp
import game.nd.extentions.*
import game.nd.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object ItemPicker : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs<PickItemUp> { (context, itemHolder, position) ->
        val world = context.world
        world.findItemsAt(position).whenHasItems { items ->
            val item = items.last()
            if (itemHolder.inventory.addItem(item)) {
                world.removeEntity(item)
                val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder"
                val verb = if (itemHolder.isPlayer) "pick up" else "picks up"
                logGameEvent("$subject $verb the $item.")
            }
        }
        Consumed
    }
}
