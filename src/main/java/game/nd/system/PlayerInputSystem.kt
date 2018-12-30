package game.nd.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import game.nd.component.Mappers
import game.nd.component.PlayerComponent
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke

class PlayerInputSystem : EntitySystem() {

    var keyStroke: KeyStroke? = null
    fun setKeyInput(it: KeyStroke) {
        keyStroke = it
    }

    override fun update(deltaTime: Float) {
        var entities = engine.getEntitiesFor(PlayerInputSystem.FAMILY)

        for (entity in entities) {
            var mc = Mappers.movements.get(entity)
            println("PlayerInputSystem: update: " + keyStroke)
            when (keyStroke?.inputType()) {

                InputType.Numpad6 -> mc.positionDelta(Position3D.create(1, 0,0))
                InputType.Numpad4 -> mc.positionDelta(Position3D.create(-1, 0,0))
                InputType.Numpad8 -> mc.positionDelta(Position3D.create(0, -1,0))
                InputType.Numpad2 -> mc.positionDelta(Position3D.create(0, 1,0))

                InputType.Numpad9 -> mc.positionDelta(Position3D.create(1, -1,0))
                InputType.Numpad7 -> mc.positionDelta(Position3D.create(-1, -1,0))
                InputType.Numpad3 -> mc.positionDelta(Position3D.create(1, 1,0))
                InputType.Numpad1 -> mc.positionDelta(Position3D.create(-1, 1,0))
            }
        }
    }

    companion object {
        private val FAMILY = Family.all(PlayerComponent::class.java).get()
    }
}