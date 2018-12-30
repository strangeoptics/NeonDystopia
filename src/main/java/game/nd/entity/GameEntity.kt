package game.nd.entity

import com.badlogic.ashley.core.Entity
import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D

class GameEntity(var tile: Tile, var position: Position3D) : Entity() {

}