package game.nd.component

import com.badlogic.ashley.core.Component
import org.hexworks.zircon.api.data.impl.Position3D

class MovementComponent : Component {

    var move = false
    var positionDelta = Position3D.create(0,0,0)

    fun positionDelta(positionDelta: Position3D) {
        this.positionDelta = positionDelta
        move = true
    }
}