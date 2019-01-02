package org.hexworks.cavesofzircon.commands

import game.nd.attribute.type.Player
import game.nd.extentions.GameCommand
import game.nd.extentions.GameEntity
import game.nd.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * A [GameCommand] representing moving [source] down at [position].
 */
data class MoveUp(override val context: GameContext,
                  override val source: GameEntity<Player>,
                  val position: Position3D) : GameCommand<EntityType>
