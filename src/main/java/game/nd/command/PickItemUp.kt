package game.nd.command

import game.nd.attribute.type.ItemHolder
import game.nd.extentions.GameCommand
import game.nd.extentions.GameEntity
import game.nd.world.GameContext
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * A [GameCommand] representing [source] picking up an item at [position].
 */
data class PickItemUp(override val context: GameContext,
                      override val source: GameEntity<ItemHolder>,
                      val position: Position3D) : GameCommand<ItemHolder>
