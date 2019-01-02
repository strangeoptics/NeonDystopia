package game.nd.attribute.type

import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType(
        name = "player"), ExperienceGainer

object Citizen : BaseEntityType(
        name = "citizen"), ExperienceGainer
