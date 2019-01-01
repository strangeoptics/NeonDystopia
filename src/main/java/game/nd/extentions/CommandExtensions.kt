package game.nd.extentions

import game.nd.world.GameContext
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.entity.EntityType

inline fun <reified T : Command<out EntityType, GameContext>> Command<out EntityType, GameContext>.whenCommandIs(
        noinline fn: (T) -> Unit): Boolean {
    return whenCommandIs(T::class, fn)
}