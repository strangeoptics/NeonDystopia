package game.nd.attribute.type

import game.nd.attribute.CryptosCounter
import game.nd.extentions.GameEntity
import game.nd.extentions.attribute
import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType(
        name = "player"), ExperienceGainer

object Citizen : BaseEntityType(
        name = "citizen"), ExperienceGainer

object Wall : BaseEntityType(
        name = "wall")

object StairsDown : BaseEntityType(
        name = "stairs down")

object StairsUp : BaseEntityType(
        name = "stairs up")