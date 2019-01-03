package game.nd.system

import game.nd.extentions.GameCommand
import game.nd.extentions.position
import game.nd.extentions.whenCommandIs
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.commands.MoveDown
import org.hexworks.cavesofzircon.commands.MoveUp

object LevelChanger : BaseFacet<GameContext>() {
    override fun executeCommand(command: GameCommand<out EntityType>): Boolean {
        return command.whenCommandIs<MoveUp> { (context, player, position) ->
            context.world.withBlockAt(player.position) { block ->
                when {
                    block.hasStairsUp -> {
                        context.world.moveEntity(player, position, position.withRelativeZ(-1))
                        context.world.scrollOneDown()
                    }
                }
            }
        } or command.whenCommandIs<MoveDown> { (context, player, position) ->
            context.world.withBlockAt(player.position) { block ->
                when {
                    block.hasStairsDown -> {
                        context.world.moveEntity(player,position, position.withRelativeZ(1))
                        context.world.scrollOneUp()
                    }
                }
            }
        }
    }
}