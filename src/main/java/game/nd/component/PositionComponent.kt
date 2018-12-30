package game.nd.component

import com.badlogic.ashley.core.Component
import org.hexworks.zircon.api.data.Position
import com.badlogic.ashley.core.ComponentMapper
import org.hexworks.zircon.api.data.impl.Position3D


class PositionComponent(var position: Position3D = Position3D.create(0,0,0)) : Component {

    //var position = Position3D.create(0,0,0)

    companion object {
        val Map = ComponentMapper.getFor(PositionComponent::class.java)
    }


}