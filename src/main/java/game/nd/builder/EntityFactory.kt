package game.nd.builder

import game.nd.attribute.*
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
                        defenseValue = 5),
                CryptosCounter())
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
                        defenseValue = 3),
                CryptosCounter())
        behaviors(RandomWalkBehavior)
        facets(Movable)
    }
}