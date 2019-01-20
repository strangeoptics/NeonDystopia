package game.nd.command

import game.nd.attribute.type.Item
import game.nd.attribute.type.ItemHolder
import game.nd.extentions.GameCommand
import game.nd.extentions.GameEntity
import game.nd.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * A [GameCommand] representing [source] dropping [item] at [position].
 */
data class DropItem(override val context: GameContext,
                    override val source: GameEntity<ItemHolder>,
                    val item: GameEntity<Item>,
                    val position: Position3D) : GameCommand<EntityType>
