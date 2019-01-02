package game.nd.system

import game.nd.command.MoveCamera
import game.nd.command.MoveTo
import game.nd.extentions.GameCommand
import game.nd.extentions.position
import game.nd.extentions.whenCommandIs
import game.nd.world.GameContext
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType

object CameraMover : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<MoveCamera> {
        (context, entity) ->

        val (world, screen, input, player) = context

        val visibleOffset = world.visibleOffset()
        val screenPos = player.position - world.visibleOffset()
        val halfHeight = world.visibleSize().yLength / 2 + 5
        val halfWidth = world.visibleSize().xLength / 2 + 5


        if (screenPos.y < halfHeight) {
            world.scrollOneBackward()
        } else
        if (screenPos.y > halfHeight) {
            world.scrollOneForward()
        } else
        if (screenPos.x < halfWidth) {
            world.scrollOneLeft()
        } else
        if (screenPos.x > halfWidth) {
            world.scrollOneRight()
        }
    }
}