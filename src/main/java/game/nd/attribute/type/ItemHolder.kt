package game.nd.attribute.type

import game.nd.attribute.Inventory
import game.nd.extentions.GameEntity
import game.nd.extentions.attribute
import org.hexworks.amethyst.api.entity.EntityType

interface ItemHolder : EntityType

val GameEntity<ItemHolder>.inventory: Inventory
    get() = attribute()
