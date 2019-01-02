package game.nd.view.fragments

import game.nd.GameConfig
import game.nd.attribute.type.Player
import game.nd.attribute.type.combatStats
import game.nd.extentions.GameEntity
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment

class PlayerStats(player: GameEntity<Player>) : Fragment {
    override val root: Component by lazy {
        var componentHeights = 0
        val innerWidth = GameConfig.SIDEBAR_WIDTH - 4

        val combatStats = player.combatStats.toComponent(innerWidth)
        componentHeights += combatStats.height + 1

        /*val hunger = player.hunger.toComponent(innerWidth)
        hunger.moveDownBy(componentHeights)
        componentHeights += hunger.height + 1

        val equipment = player.equipment.toComponent(innerWidth)
        equipment.moveDownBy(componentHeights)
        componentHeights += equipment.height + 1

        val zircons = player.zirconCounter.toComponent(innerWidth)
        zircons.moveDownBy(componentHeights)
        componentHeights += zircons.height + 1*/

        Components.panel()
                .withSize(Sizes.create(GameConfig.SIDEBAR_WIDTH-2, componentHeights + 2))
                .wrapWithBox()
                .build().apply {
                    addComponent(combatStats)
                    /*addComponent(hunger)
                    addComponent(equipment)
                    addComponent(zircons)*/
                }
    }
}
