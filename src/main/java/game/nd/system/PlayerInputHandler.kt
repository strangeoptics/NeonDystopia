package game.nd.system

import game.nd.command.LookAt
import game.nd.command.MoveTo
import game.nd.command.Open
import game.nd.command.Steal
import game.nd.extentions.GameEntity
import game.nd.extentions.logGameEvent
import game.nd.extentions.position
import game.nd.view.ShopView
import game.nd.view.StartView
import game.nd.view.dialogs.openHelpDialog
import game.nd.world.GameContext
import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.commands.MoveDown
import org.hexworks.cavesofzircon.commands.MoveUp
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.whenKeyStroke

/**
 * This [System] is responsible for figuring out what to do based on
 * the input which was received from the player.
 */
object PlayerInputHandler : BaseBehavior<GameContext>() {


    var command : Command<EntityType, GameContext>? = null

    override fun update(entity: GameEntity<out EntityType>, context: GameContext): Boolean {
        val (world, screen, input, player) = context
        var update = true

        input.whenKeyStroke {
            ks -> (
                    if(ks.getCharacter() == ' ') {
                        val currentPos = player.position
                        val newPos = when (ks.inputType()) {
                            InputType.Numpad6 -> currentPos.withRelativeX(1)
                            InputType.Numpad4 -> currentPos.withRelativeX(-1)
                            InputType.Numpad8 -> currentPos.withRelativeY(-1)
                            InputType.Numpad2 -> currentPos.withRelativeY(1)

                            InputType.Numpad9 -> currentPos.withRelativeX(1).withRelativeY(-1)
                            InputType.Numpad7 -> currentPos.withRelativeX(-1).withRelativeY(-1)
                            InputType.Numpad3 -> currentPos.withRelativeX(1).withRelativeY(1)
                            InputType.Numpad1 -> currentPos.withRelativeX(-1).withRelativeY(1)
                            InputType.Space ->  {world.toggleTimeModes(world, screen)
                                    null}
                            else -> {
                                null
                            }
                        }
                        if(newPos != null) {
                            world.whenHasBlockAt(newPos) { block ->
                                if(command != null) {
                                    block.withTopNonPlayerEntity {
                                        when(command) {
                                            is Steal -> player.executeCommand(Steal(context, player, it))
                                            is Open -> player.executeCommand(Open(context, player, it))
                                        }

                                    }
                                    command = null
                                } else
                                if (block.isOccupied) {
                                    player.executeCommand(LookAt(context, player, newPos))
                                } else {
                                    player.executeCommand(MoveTo(context, player, newPos))
                                }
                            }
                        }
                    } else {
                        val stop = when(ks.getCharacter()) {
                            's' -> {
                                command =  Steal(context, player, player)
                                logGameEvent("steal: which direction")
                                false
                            }
                            'o' -> {
                                command = Open(context, player, player)
                                logGameEvent("open: which direction")
                                false
                            }
                            else -> false
                        }
                        update = stop

                        when(ks.getCharacter()) {
                            'h' -> openHelpDialog(screen)
                            '>' -> player.executeCommand(MoveDown(context, player, player.position))
                            '<' -> player.executeCommand(MoveUp(context, player, player.position))
                            'l' -> player.executeCommand(LookAt(context, player, player.position))
                            'p' -> world.playView.replaceWith(ShopView(world.playView))
                        }

                    }
                )
        }

        return update
    }

}