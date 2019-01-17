package game.nd.view

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.kotlin.onMouseReleased
import org.hexworks.zircon.api.mvc.base.BaseView

class ShopView(private val playView: PlayView) : BaseView() {

    override fun onDock() {
        val backButton = Components.button()
                .withAlignmentWithin(screen, ComponentAlignment.LEFT_CENTER)
                .withText("Back")
                .wrapSides(false)
                .withBoxType(BoxType.SINGLE)
                .wrapWithShadow()
                .wrapWithBox()
                .build()

        screen.addComponent(backButton)

        backButton.onMouseReleased {
            replaceWith(playView)
            close()
        }
    }
}