package game.nd.view.dialogs

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class HelpDialog(screen: Screen) : Dialog(screen) {

    override val container = Components.panel()
            .withTitle("Help")
            .withSize(44, 30)
            .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
            .wrapWithBox()
            .build().apply {
                addComponent(Components.textBox()
                        .withContentWidth(42)
                        .addNewLine()
                        .addHeader("Neon Dystopia")
                        .addParagraph("""
                            Descend to the sewers of NeonDystopia. Make a fortune to rise out of
                            your poor class with no future. Find the finest mutated animals to suite the tongue
                            of the rich and wealthy of the city. Sell them to the food stalls,
                            restaurant and top private chefs.""".trimIndent())
                        .addNewLine())

                addComponent(Components.textBox()
                        .withContentWidth(27)
                        .withPosition(0, 11)
                        .addHeader("Movement:")
                        .addListItem("numpad: Movement")
                        .addListItem("r: Move up / exit")
                        .addListItem("f: Move down / exit"))

                addComponent(Components.textBox()
                        .withContentWidth(40)
                        .withPosition(0, 18)
                        .addHeader("Navigation:")
                        .addListItem("[Tab]: Focus next")
                        .addListItem("[Shift] + [Tab]: Focus previous")
                        .addListItem("[Space]: Activate focused item")
                        .addListItem("Or you can use the mouse to click on items"))

                addComponent(Components.textBox()
                        .withContentWidth(21)
                        .withPosition(28, 11)
                        .addHeader("Actions:")
                        .addListItem("(i)nventory")
                        .addListItem("(p)ick up")
                        .addListItem("(l)oot")
                        .addListItem("(h)elp")
                        .addListItem("(e)quipment"))
            }
}
