package game.nd.attribute

import game.nd.util.Direction4
import org.hexworks.amethyst.api.Attribute

class PassingDescription(vararg pairs: Pair<Direction4, String>) : Attribute {


    var map = HashMap<Direction4, String>().apply { putAll(pairs)}
    //var dir: Direction4, var description: String

}