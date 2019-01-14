package game.nd.attribute

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.data.impl.Position3D

class StartStop(start: Position3D = Position3D.unknown(), stop: Position3D = Position3D.unknown()) : Attribute {

    private val startProperty = createPropertyFrom(start)
    var startPosition: Position3D by startProperty.asDelegate()

    private val stopProperty = createPropertyFrom(stop)
    var stopPosition: Position3D by stopProperty.asDelegate()
}