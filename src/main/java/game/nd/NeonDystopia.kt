package game.nd

import game.nd.view.StartView
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.SwingApplications


@Suppress("ConstantConditionIf")
fun main(args: Array<String>) {

    val application = SwingApplications.startApplication(GameConfig.buildAppConfig())
    application.dock(StartView())
}

