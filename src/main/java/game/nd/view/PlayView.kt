package game.nd.view

import game.nd.GameConfig
import game.nd.block.GameBlock
import game.nd.builder.GameTileRepository
import game.nd.world.WorldImpl
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.grid.TileGrid

class PlayView(tileGrid: TileGrid) : BaseView(tileGrid) {

    private val screenSize = screen.size
    private val sidebarWidth = 20
    private val logAreaHeight = 10
    private val VISIBLE_SIZE = Sizes.create3DSize(GameConfig.WINDOW_WIDTH-sidebarWidth, GameConfig.WINDOW_HEIGHT-logAreaHeight, 1)
    private val ACTUAL_SIZE = Sizes.create3DSize(200, 200, 1)

    private val playerHp: TextArea
    private val playerHunger: TextArea

    init {

        val statsPanel = Components.panel()
                .withSize(Sizes.create(sidebarWidth, GameConfig.WINDOW_HEIGHT))
                .withTitle("Stats")
                .withAlignmentWithin(screen, ComponentAlignment.TOP_LEFT)
                .wrapWithBox()
                .build()
        playerHp = Components.textArea()
                .withSize(Sizes.create(statsPanel.contentSize.width, 1))
                .build()
        playerHp.disable()
        playerHunger = Components.textArea()
                .withSize(Sizes.create(statsPanel.contentSize.width, 1))
                .withPosition(Positions.create(0, 1))
                .build()
        playerHunger.disable()

        statsPanel.addComponent(playerHp)
        statsPanel.addComponent(playerHunger)



        val logPanel = Components.panel()
                .withSize(Sizes.create(GameConfig.WINDOW_WIDTH-sidebarWidth, logAreaHeight))
                .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
                .wrapWithBox()
                .withTitle("Game log")
                .build()
        val logArea = Components.logArea()
                .withSize(logPanel.size - Sizes.create(2, 2))
                .build()
        logPanel.addComponent(logArea)


        val gameArea = WorldImpl(VISIBLE_SIZE, ACTUAL_SIZE)
        makeCaves(gameArea)
        var block = gameArea.fetchBlockAt(Position3D.create(5,5,0))
        block.get().defaultTile = GameTileRepository.PLAYER
        block.get().layers.add(GameTileRepository.PLAYER)



        screen.addComponent(statsPanel)
        screen.addComponent(logPanel)
        screen.addComponent(GameComponents.newGameComponentBuilder<Tile, GameBlock>()
                .withVisibleSize(VISIBLE_SIZE)
                .withGameArea(gameArea).withPosition(sidebarWidth, 0)
                .build())

    }

    private fun makeCaves(gameArea: GameArea<Tile, GameBlock>, smoothTimes: Int = 8) {
        val width = gameArea.actualSize().xLength
        val height = gameArea.actualSize().yLength
        var tiles: MutableMap<Position, Tile> = mutableMapOf()
        gameArea.actualSize().to2DSize().fetchPositions().forEach { pos ->
            tiles[pos] = if (Math.random() < 0.5) FLOOR else WALL
        }
        val newTiles: MutableMap<Position, Tile> = mutableMapOf()
        for (time in 0 until smoothTimes) {

            for (x in 0 until width) {
                for (y in 0 until height) {
                    var floors = 0
                    var rocks = 0

                    for (ox in -1..1) {
                        for (oy in -1..1) {
                            if (x + ox < 0 || x + ox >= width || y + oy < 0
                                    || y + oy >= height)
                                continue

                            if (tiles[Positions.create(x + ox, y + oy)] === FLOOR)
                                floors++
                            else
                                rocks++
                        }
                    }
                    newTiles[Positions.create(x, y)] = if (floors >= rocks) FLOOR else WALL
                }
            }
            tiles = newTiles
        }
        tiles.forEach { pos, tile ->
            val pos3D = Positions.from2DTo3D(pos)
            var gb = GameBlock.create()
            gb.defaultTile = tile
            gameArea.setBlockAt(pos3D, gb)
        }
    }
}

private val WALL = Tiles.newBuilder()
        .withCharacter('#')
        .withForegroundColor(TileColors.fromString("#999999"))
        .buildCharacterTile()

private val FLOOR = Tiles.newBuilder()
        .withCharacter(Symbols.INTERPUNCT)
        .withForegroundColor(ANSITileColor.YELLOW)
        .buildCharacterTile()