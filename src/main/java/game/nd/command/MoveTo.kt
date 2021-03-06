package game.nd.command

import game.nd.extentions.GameCommand
import game.nd.extentions.GameEntity
import game.nd.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * A [GameCommand] representing moving [source] to [position].
 */
data class MoveTo(override val context: GameContext,
                  override val source: GameEntity<EntityType>,
                  val position: Position3D) : GameCommand<EntityType>