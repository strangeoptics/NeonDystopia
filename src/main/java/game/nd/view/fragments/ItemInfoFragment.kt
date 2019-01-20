package game.nd.view.fragments

import game.nd.GameConfig
import game.nd.attribute.DisplayableAttribute
import game.nd.attribute.type.Item
import game.nd.attribute.type.iconTile
import game.nd.extentions.GameEntity
import game.nd.extentions.attributesOfType
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.data.Size

class ItemInfoFragment(selectedItem: ObservableValue<GameEntity<Item>>, size: Size) : Fragment {

    override val root = Components.panel()
            .withSize(size)
            .build().apply {
                val item = selectedItem.value
                val header = Components.header()
                        .withText("Info")
                        .build()
                val itemIcon = Components.icon()
                        .withIcon(item.iconTile)
                        .withPosition(0, 2)
                        .build()
                val itemName = Components.label()
                        .withText(item.name)
                        .withSize(size.width - 1, 1)
                        .withPosition(1, 2)
                        .build()
                val itemDescription = Components.paragraph()
                        .withText(item.description)
                        .withSize(size.width, 5)
                        .withPosition(0, 4)
                        .build()

                val otherAttributesPanel = Components.panel()
                        .withSize(size.width, size.height - 11)
                        .withPosition(0, 10)
                        .build()

                fun updateOtherAttributes(item: GameEntity<Item>) {
                    otherAttributesPanel.clear()
                    var nextPos = Positions.create(0, 0)
                    item.attributesOfType<DisplayableAttribute>().forEach { attr ->
                        val comp = attr.toComponent(size.width)
                        comp.moveTo(nextPos)
                        otherAttributesPanel.addComponent(comp)
                        nextPos = nextPos.withRelativeX(comp.height + 1)
                    }
                    otherAttributesPanel.applyColorTheme(GameConfig.THEME)
                }

                updateOtherAttributes(item)

                selectedItem.onChange { (_, _, newItem) ->
                    itemName.textProperty.value = newItem.name
                    itemIcon.iconProperty.value = newItem.iconTile
                    itemDescription.textProperty.value = newItem.description
                    updateOtherAttributes(newItem)
                }

                addComponent(header)
                addComponent(itemIcon)
                addComponent(itemName)
                addComponent(itemDescription)
                addComponent(otherAttributesPanel)
            }
}
