package game.nd.builder

import game.nd.attribute.*
import game.nd.attribute.type.*
import game.nd.extentions.newGameEntityOfType
import game.nd.system.*
import org.hexworks.cavesofzircon.attributes.flags.VisionBlocker
import org.hexworks.zircon.api.data.Tile

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
        facets(Movable, CameraMover, BlockInspector, LevelChanger)
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

    fun newWall() = newGameEntityOfType(Wall) {
        attributes(VisionBlocker,
                EntityPosition(),
                BlockOccupier,
                EntityTile(GameTileRepository.WALL_VARIATION_D_V))
    }
    fun newWall(tile: Tile) = newGameEntityOfType(Wall) {
        attributes(VisionBlocker,
                EntityPosition(),
                BlockOccupier,
                EntityTile(tile))
    }

    fun newStairsDown() = newGameEntityOfType(StairsDown) {
        attributes(EntityTile(GameTileRepository.STAIRS_DOWN),
                EntityPosition())
    }

    fun newStairsUp() = newGameEntityOfType(StairsUp) {
        attributes(EntityTile(GameTileRepository.STAIRS_UP),
                EntityPosition())
    }
}