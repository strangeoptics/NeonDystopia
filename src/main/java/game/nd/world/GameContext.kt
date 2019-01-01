package game.nd.world

import game.nd.attribute.type.Player
import game.nd.extentions.GameEntity
import org.hexworks.amethyst.api.Context
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.screen.Screen

data class GameContext(val world: WorldImpl,
                       val screen: Screen,
                       val input: Input,
                       val player: GameEntity<Player>)
                       : Context