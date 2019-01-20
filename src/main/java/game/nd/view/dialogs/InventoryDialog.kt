package game.nd.view.dialogs

import game.nd.attribute.Inventory
import game.nd.command.DropItem
import game.nd.extentions.attribute
import game.nd.extentions.position
import game.nd.view.fragments.ItemInfoFragment
import game.nd.view.fragments.ItemListFragment
import game.nd.world.GameContext
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.kotlin.onMouseReleased

class InventoryDialog(context: GameContext) : Dialog(context.screen) {

    override val container = Components.panel()
            .withTitle("Inventory")
            .withSize(42, 20)
            .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
            .wrapWithBox()
            .build().also {inventoryPanel ->
                val player = context.player
                val playerPosition = player.position
                val inventory = player.attribute<Inventory>()
                val itemListFragment = ItemListFragment(inventory, 20)
                inventoryPanel.addFragment(itemListFragment)
                inventoryPanel.addFragment(ItemInfoFragment(
                        selectedItem = itemListFragment.selectedProperty,
                        size = Sizes.create(19, 17)).apply {
                    root.moveRightBy(20)
                })

                val drop = Components.button()
                        .withText("Drop")
                        .withAlignmentWithin(inventoryPanel, ComponentAlignment.BOTTOM_LEFT)
                        .build().apply {
                            onMouseReleased {
                                itemListFragment.fetchSelectedItem().map { selectedItem ->
                                    itemListFragment.removeSelectedItem()
                                    player.executeCommand(DropItem(
                                            context = context,
                                            source = player,
                                            item = selectedItem,
                                            position = playerPosition))
                                }
                            }
                        }

                inventoryPanel.addComponent(drop)

            }
}