package game.nd.command

import game.nd.extentions.GameCommand
import game.nd.extentions.GameEntity
import game.nd.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class MoveCamera(override val context: GameContext, override val source: GameEntity<EntityType>) : GameCommand<EntityType>