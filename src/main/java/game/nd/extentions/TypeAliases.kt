package game.nd.extentions

import game.nd.world.GameContext
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

/**
 * Fits any [Entity] type we use.
 */
typealias AnyGameEntity = Entity<EntityType, GameContext>

/**
 * Specializes [Entity] with our [GameContext] type.
 */
typealias GameEntity<T> = Entity<T, GameContext>

typealias GameCommand<T> = Command<T, GameContext>
