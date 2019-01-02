package game.nd.attribute.type

import game.nd.attribute.CryptosCounter
import game.nd.extentions.GameEntity
import game.nd.extentions.attribute
import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType(
        name = "player"), ExperienceGainer

object Citizen : BaseEntityType(
        name = "citizen"), ExperienceGainer

val GameEntity<Player>.cryptoCounter: CryptosCounter
    get() = attribute()