package game.nd.attribute.type

import game.nd.attribute.ItemCombatStats
import game.nd.extentions.GameEntity
import game.nd.extentions.attribute

interface Weapon : Item

val GameEntity<Weapon>.attackValue: Int
    get() = attribute<ItemCombatStats>().attackValue

val GameEntity<Weapon>.defenseValue: Int
    get() = attribute<ItemCombatStats>().defenseValue
