package game.nd.view

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.kotlin.onMouseReleased

class StartView(tileGrid: TileGrid) : BaseView(tileGrid) {

    init {
        val msg = "Neon Dystopia"
        val header = Components.textBox()
                .withContentWidth(msg.length)
                .addHeader(msg)
                .addNewLine()
                .withAlignmentWithin(tileGrid, ComponentAlignment.CENTER)
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
            /*val game = GameBuilder(
                    worldSize = WORLD_SIZE,
                    gridSize = tileGrid.size).buildGame()*/
            PlayView(tileGrid).dock()

        }

        screen.addComponent(header)
        screen.addComponent(startButton)
    }
}