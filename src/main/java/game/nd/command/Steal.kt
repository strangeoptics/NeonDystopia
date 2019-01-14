package game.nd.command

import game.nd.attribute.type.Player
import game.nd.extentions.GameCommand
import game.nd.extentions.GameEntity
import game.nd.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Steal(override val context: GameContext,
                 override val source: GameEntity<Player>,
                 var target: GameEntity<EntityType>) : GameCommand<EntityType>