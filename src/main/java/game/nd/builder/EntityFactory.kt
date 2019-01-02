package game.nd.builder

import game.nd.attribute.BlockOccupier
import game.nd.attribute.CombatStats
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
                EntityTile(GameTileRepository.PLAYER),
                CombatStats.create(
                        maxHp = 100,
                        attackValue = 10,
                        defenseValue = 5))
        behaviors(PlayerInputHandler)
        facets(Movable, CameraMover, BlockInspector)
    }

    fun newCitizen() = newGameEntityOfType(Citizen) {
        attributes(
                BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.CITIZEN),
                CombatStats.create(
                        maxHp = 50,
                        attackValue = 5,
                        defenseValue = 3))
        behaviors(RandomWalkBehavior)
        facets(Movable)
    }
}