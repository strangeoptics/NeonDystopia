package game.nd.entity

import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.zircon.api.data.Tile

interface Entity {

    val id: Identifier
    val tile: Tile
}