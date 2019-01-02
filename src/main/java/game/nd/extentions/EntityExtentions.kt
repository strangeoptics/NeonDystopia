package game.nd.extentions

import game.nd.attribute.BlockOccupier
import game.nd.attribute.EntityPosition
import game.nd.attribute.EntityTile
import game.nd.attribute.type.Player
import game.nd.attribute.type.StairsDown
import game.nd.attribute.type.StairsUp
import game.nd.world.GameContext
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.datatypes.extensions.orElseThrow
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.full.isSubclassOf

var AnyGameEntity.position
    get() = attribute(EntityPosition::class).orElseThrow {
        IllegalArgumentException("This Entity has no EntityPosition")
    }.position
    set(value) {
        attribute(EntityPosition::class).map {
            it.position = value
        }
    }

val AnyGameEntity.tile: Tile
    get() = this.attribute<EntityTile>().tile

val AnyGameEntity.occupiesBlock: Boolean
    get() = hasAttribute<BlockOccupier>()

val AnyGameEntity.isPlayer: Boolean
    get() = this.type == Player

val AnyGameEntity.isStairsDown: Boolean
    get() = this.type is StairsDown

val AnyGameEntity.isStairsUp: Boolean
    get() = this.type is StairsUp

inline fun <reified T : Attribute> AnyGameEntity.attribute(): T = attribute(T::class).orElseThrow {
    NoSuchElementException("Entity '$this' has no property with type '${T::class.simpleName}'.")
}

inline fun <reified T : Attribute> AnyGameEntity.hasAttribute() = attribute(T::class).isPresent

inline fun <reified T : Attribute> AnyGameEntity.whenHasAttribute(crossinline fn: (T) -> Unit) = attribute(T::class).map(fn)



@Suppress("UNCHECKED_CAST")
inline fun <reified T : EntityType> AnyGameEntity.whenTypeIs(fn: (Entity<T, GameContext>) -> Unit) {
    if (this.type::class.isSubclassOf(T::class)) {
        fn(this as Entity<T, GameContext>)
    }
}