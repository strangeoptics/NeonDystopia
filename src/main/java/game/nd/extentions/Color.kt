package game.nd.extentions

import org.hexworks.zircon.api.color.TileColor

fun TileColor.lerp(b: TileColor, t: Float): TileColor {
    return TileColor.create(
            (red + (b.red - red) * t).toInt(),
            (green + (b.green - green) * t).toInt(),
            (blue + (b.blue - blue) * t).toInt(),
            (alpha + (b.alpha - alpha) * t).toInt()
    )
}