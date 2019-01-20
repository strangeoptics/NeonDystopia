package game.nd.attribute.type

import game.nd.attribute.EntityTile
import game.nd.attribute.ItemIcon
import game.nd.extentions.GameEntity
import game.nd.extentions.attribute
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.data.Tile

interface Item : EntityType

val GameEntity<Item>.tile: Tile
    get() = attribute<EntityTile>().tile

val GameEntity<Item>.iconTile: GraphicTile
    get() = attribute<ItemIcon>().iconTile
