package game.nd.util

import org.hexworks.zircon.api.data.impl.Position3D

enum class Direction4 {
    UP,
    DOWN,
    RIGHT,
    LEFT,
    NONE;

    companion object {
        @JvmStatic
        fun from(newPos: Position3D, oldPos: Position3D): Direction4 {
            val delta = newPos.minus(oldPos)
            if(delta.x < 0) return LEFT
            if(delta.x > 0) return RIGHT
            if(delta.y > 0) return DOWN
            if(delta.y < 0) return UP
            return NONE
        }
    }
}