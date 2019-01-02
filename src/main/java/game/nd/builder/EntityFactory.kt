package game.nd.builder

import game.nd.attribute.BlockOccupier
import game.nd.attribute.EntityPosition
import game.nd.attribute.EntityTile
import game.nd.attribute.type.Citizen
import game.nd.attribute.type.Player
import game.nd.extentions.newGameEntityOfType
import game.nd.system.*

object EntityFactory {

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(
                BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.PLAYER))
        behaviors(PlayerInputHandler)
        facets(Movable, CameraMover, BlockInspector)
    }

    fun newCitizen() = newGameEntityOfType(Citizen) {
        attributes(
                BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.CITIZEN))
        behaviors(RandomWalkBehavior)
        facets(Movable)
    }
}