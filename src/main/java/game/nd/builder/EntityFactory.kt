package game.nd.builder

import game.nd.attribute.*
import game.nd.attribute.type.*
import game.nd.extentions.newGameEntityOfType
import game.nd.system.*
import org.hexworks.cavesofzircon.attributes.flags.VisionBlocker
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.resource.BuiltInGraphicTilesetResource

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
                CryptosCounter(),
                Inventory(10))
        behaviors(PlayerInputHandler)
        facets(Movable, CameraMover, BlockInspector, LevelChanger, Stealing, Opener, ItemDropper)
    }

    fun newCitizen(position: Position3D = Position3D.unknown()) = newGameEntityOfType(Citizen) {
        attributes(
                BlockOccupier,
                EntityPosition(position),
                EntityTile(GameTileRepository.CITIZEN),
                CombatStats.create(
                        maxHp = 50,
                        attackValue = 5,
                        defenseValue = 3),
                CryptosCounter())
        behaviors(RandomWalkBehavior)
        facets(Movable)
    }

    fun newHotelOwner(position: Position3D = Position3D.unknown()) = newGameEntityOfType(HotelOwner) {
        attributes(
                BlockOccupier,
                EntityPosition(position),
                EntityTile(GameTileRepository.HOTEL_OWNER),
                CombatStats.create(
                        maxHp = 50,
                        attackValue = 5,
                        defenseValue = 3),
                CryptosCounter())
        //behaviors()
        facets(Movable)
    }

    fun newCar(start: Position3D = Position3D.unknown(), stop: Position3D = Position3D.unknown(), direction: Position3D = Position3D.unknown(), speed: Int = 1) = newGameEntityOfType(Car) {
        attributes(
            BlockOccupier,
            EntityPosition(start),
            StartStop(start, stop, direction, speed),
            EntityTiles(GameTileRepository.CAR_SMALL))
        behaviors(CarBehavior)
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

    fun newWindow(tile: Tile) = newGameEntityOfType(Window) {
        attributes(EntityPosition(),
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

    fun newDoor(tile: Tile, locked: Boolean = true) = newGameEntityOfType(Door) {
        attributes(EntityTile(tile),
                EntityPosition(),
                BlockOccupier,
                Openable(locked))
    }

    fun newBed(tile: Tile) = newGameEntityOfType(Bed) {
        attributes(EntityTile(tile),
                EntityPosition())
    }

    fun newCounter(tile: Tile) = newGameEntityOfType(Counter) {
        attributes(EntityTile(tile),
                EntityPosition())
    }

    // items /////////////////////

    //// Armor //////////////////

    fun newJacket() = newGameEntityOfType(Jacket) {
        attributes(ItemCombatStats(combatItemType = "Armor"),
                EntityTile(),
                EntityPosition(),
                ItemIcon(Tiles.newBuilder()
                        .withName("Leather jacket")
                        .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                        .buildGraphicTile()))
    }

    //// Weapons ///////////////

    fun newDagger() = newGameEntityOfType(Dagger) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("Dagger")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 4,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.dagger()))
    }
}