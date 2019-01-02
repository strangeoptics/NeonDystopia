package game.nd.attribute.type

import game.nd.attribute.CombatStats
import game.nd.extentions.GameEntity
import game.nd.extentions.attribute
import org.hexworks.amethyst.api.entity.EntityType

interface ExperienceGainer : EntityType

/*val GameEntity<ExperienceGainer>.experience: Experience
    get() = attribute()*/

val GameEntity<ExperienceGainer>.combatStats: CombatStats
    get() = attribute()

