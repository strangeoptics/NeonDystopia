package game.nd.attribute.type

import game.nd.attribute.CryptosCounter
import game.nd.extentions.GameEntity
import game.nd.extentions.attribute
import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType(
        name = "player"), ExperienceGainer, ItemHolder

object Citizen : BaseEntityType(
        name = "citizen"), ExperienceGainer

object HotelOwner : BaseEntityType(
        name = "hotel owner"), ExperienceGainer

object Car : BaseEntityType(
        name = "Car")

object Wall : BaseEntityType(
        name = "wall")

object Window : BaseEntityType(
        name = "window")

object StairsDown : BaseEntityType(
        name = "stairs down")

object StairsUp : BaseEntityType(
        name = "stairs up")

object Door : BaseEntityType( name = "door")

object Bed : BaseEntityType( name = "Bed")

object Counter : BaseEntityType( name = "Counter")

object NoItem : BaseEntityType(
        name = "Select an item",
        description = "Select an item"), Item

// Armor //////////////

object Jacket : BaseEntityType(
        name = "Leather jacket",
        description = "Dirty and rugged jacket made of leather."), Armor

// Weapons ///////////

object Dagger : BaseEntityType(
        name = "Rusty Dagger",
        description = "A small, rusty dagger made of some metal alloy."), Weapon