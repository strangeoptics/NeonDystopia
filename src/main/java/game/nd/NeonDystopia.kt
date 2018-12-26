package game.nd

import game.nd.view.StartView
import org.hexworks.zircon.api.SwingApplications


@Suppress("ConstantConditionIf")
fun main(args: Array<String>) {

    StartView(SwingApplications.startTileGrid(GameConfig.buildAppConfig())).dock()

}

