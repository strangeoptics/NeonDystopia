package game.nd.attribute

import game.nd.builder.GameTileRepository
import org.hexworks.amethyst.api.Attribute
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile

data class EntityTiles(val tiles : ArrayList<ArrayList<CharacterTile>>) : Attribute {

    val bb = Position.create(tiles[0].size, tiles.size)

    fun getTile(pos: Position) : Tile {
        //println(pos)
        //println(""+pos + " " + tiles.get(pos.y)[pos.x].character)
        return tiles.get(pos.y)[pos.x]
    }

}
