package game.nd.view.fragments

import game.nd.attribute.EntityTile
import game.nd.attribute.Inventory
import game.nd.attribute.ItemIcon
import game.nd.attribute.type.Item
import game.nd.attribute.type.NoItem
import game.nd.extentions.GameEntity
import game.nd.extentions.newGameEntityOfType
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.kotlin.onSelection
import org.hexworks.zircon.api.resource.BuiltInGraphicTilesetResource

class ItemListFragment(inventory: Inventory, width: Int) : Fragment {

    val selectedProperty: Property<GameEntity<Item>> = createPropertyFrom(NO_ITEM)

    private val rbg: RadioButtonGroup

    fun fetchSelectedItem(): Maybe<GameEntity<Item>> {
        return Maybe.ofNullable(if (selectedProperty.value == NO_ITEM) null else selectedProperty.value)
    }

    fun removeSelectedItem() {
        rbg.fetchSelectedOption().map {
            rbg.removeOption(it)
        }
    }

    override val root = Components.panel()
            .withSize(width, inventory.size + 2)
            .build().apply {
                rbg = Components.radioButtonGroup()
                        .withSize(width, inventory.size)
                        .withPosition(0, 2)
                        .build()
                inventory.items.forEach { item ->
                    rbg.addOption(item.id.toString(), item.name)
                }
                rbg.onSelection {
                    inventory.findItemBy(IdentifierFactory.fromString(it.key)).map { item ->
                        selectedProperty.value = item
                    }
                }
                addComponent(Components.header()
                        .withText("Items"))
                addComponent(rbg)
            }

    companion object {
        private val NO_ITEM = newGameEntityOfType(NoItem) {
            attributes(EntityTile(Tiles.empty()),
                    ItemIcon(Tiles.newBuilder()
                            .withName("Hole")
                            .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                            .buildGraphicTile()))
        }
    }
}
