package game.nd.attribute.type

import game.nd.attribute.ItemCombatStats
import game.nd.extentions.attribute
import game.nd.world.GameContext
import org.hexworks.amethyst.api.entity.Entity

interface Armor : Item

val Entity<Armor, GameContext>.attackValue: Int
    get() = attribute<ItemCombatStats>().attackValue

val Entity<Armor, GameContext>.defenseValue: Int
    get() = attribute<ItemCombatStats>().defenseValue
