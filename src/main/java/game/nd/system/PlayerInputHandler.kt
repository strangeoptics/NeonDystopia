package game.nd.system

import game.nd.command.MoveTo
import game.nd.extentions.GameEntity
import game.nd.extentions.position
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.whenKeyStroke

/**
 * This [System] is responsible for figuring out what to do based on
 * the input which was received from the player.
 */
object PlayerInputHandler : BaseBehavior<GameContext>() {

    override fun update(entity: GameEntity<out EntityType>, context: GameContext): Boolean {
        val (world, screen, input, player) = context

        input.whenKeyStroke {
            ks -> (
                    if(ks.getCharacter() == ' ') {
                        val currentPos = player.position
                        val newPos = when (ks.inputType()) {
                            InputType.Numpad6 -> currentPos.withRelativeX(1)
                            InputType.Numpad4 -> currentPos.withRelativeX(-1)
                            InputType.Numpad8 -> currentPos.withRelativeY(-1)
                            InputType.Numpad2 -> currentPos.withRelativeY(1)

                            InputType.Numpad9 -> currentPos.withRelative(Position3D.create(1, -1,0))
                            InputType.Numpad7 -> currentPos.withRelative(Position3D.create(-1, -1,0))
                            InputType.Numpad3 -> currentPos.withRelative(Position3D.create(1, 1,0))
                            InputType.Numpad1 -> currentPos.withRelative(Position3D.create(-1, 1,0))
                            else -> {
                                null
                            }
                        }
                        if(newPos != null) {
                            player.executeCommand(MoveTo(context, player, newPos))
                        }
                    }
                )
        }

        return true
    }

}