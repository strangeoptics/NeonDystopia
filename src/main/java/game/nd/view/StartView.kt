package game.nd.view

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.kotlin.onMouseReleased
import org.hexworks.zircon.api.mvc.base.BaseView

class StartView : BaseView() {

    override fun onDock() {

        val msg = "Neon Dystopia"
        val header = Components.textBox()
                .withContentWidth(msg.length)
                .addHeader(msg)
                .addNewLine()
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build()
        val startButton = Components.button()
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_CENTER)
                .withText("Start!")
                .wrapSides(false)
                .withBoxType(BoxType.SINGLE)
                .wrapWithShadow()
                .wrapWithBox()
                .build()

       startButton.onMouseReleased {
           replaceWith(PlayView())
           close()
        }

        screen.addComponent(header)
        screen.addComponent(startButton)

    }
}