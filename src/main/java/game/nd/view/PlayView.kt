package game.nd.view

import game.nd.GameConfig
import game.nd.block.GameBlock
import game.nd.builder.EntityFactory
import game.nd.builder.GameTileRepository
import game.nd.events.GameLogEvent
import game.nd.extentions.position
import game.nd.world.WorldImpl
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.onKeyStroke
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.internal.Zircon
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

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
                .withTitle("Log")
                .build().also { parent ->
                    val logArea = Components.logArea()
                            .withSize(parent.size - Sizes.create(2, 2))
                            .build()
                    parent.addComponent(logArea)
                    Zircon.eventBus.subscribe<GameLogEvent> { (text) ->
                        logArea.addParagraph(text, withNewLine = false, withTypingEffectSpeedInMs = 20)
                    }
                }
        /*val logArea = Components.logArea()
                .withSize(logPanel.size - Sizes.create(2, 2))
                .build()*/
        //logPanel.addComponent(logArea)


        val gameArea = WorldImpl(VISIBLE_SIZE, ACTUAL_SIZE)
        //makeCaves(gameArea)
        loadRexGameArea(gameArea)
        var block = gameArea.fetchBlockAt(gameArea.player.position)
        block.get().addEntity(gameArea.player)

        var citizen0 = EntityFactory.newCitizen()
        citizen0.position = Position3D.create(40,40, 0)
        gameArea.engine.addEntity(citizen0)
        gameArea.fetchBlockAt(citizen0.position).get().addEntity(citizen0)
        //gameArea.fetchBlockAt(Position3D.create(30,35,0)).get().addEntity(gameArea.enemy0)
        //gameArea.fetchBlockAt(Position3D.create(40,35,0)).get().addEntity(gameArea.enemy1)


        screen.addComponent(statsPanel)
        screen.addComponent(logPanel)
        screen.addComponent(GameComponents.newGameComponentBuilder<Tile, GameBlock>()
                .withVisibleSize(VISIBLE_SIZE)
                .withGameArea(gameArea).withPosition(sidebarWidth, 0)
                .build())

        screen.onKeyStroke {

            gameArea.update(screen, it)
            //gameArea.playerInputSystem.setKeyInput(it)
            /*if(it.isCtrlDown()) {
                when (it.inputType()) {
                    InputType.ArrowRight -> gameArea.scrollOneRight()
                    InputType.ArrowLeft -> gameArea.scrollOneLeft()
                    InputType.ArrowUp -> gameArea.scrollOneBackward()
                    InputType.ArrowDown -> gameArea.scrollOneForward()
                }
            } else {
                when (it.inputType()) {
                    InputType.Numpad6 -> gameArea.moveEntity(gameArea.player, gameArea.player.position.withRelative(Position3D.create(1, 0,0)))
                    InputType.Numpad4 -> gameArea.moveEntity(gameArea.player, gameArea.player.position.withRelative(Position3D.create(-1, 0,0)))
                    InputType.Numpad8 -> gameArea.moveEntity(gameArea.player, gameArea.player.position.withRelative(Position3D.create(0, -1,0)))
                    InputType.Numpad2 -> gameArea.moveEntity(gameArea.player, gameArea.player.position.withRelative(Position3D.create(0, 1,0)))

                    InputType.Numpad9 -> gameArea.moveEntity(gameArea.player, gameArea.player.position.withRelative(Position3D.create(1, -1,0)))
                    InputType.Numpad7 -> gameArea.moveEntity(gameArea.player, gameArea.player.position.withRelative(Position3D.create(-1, -1,0)))
                    InputType.Numpad3 -> gameArea.moveEntity(gameArea.player, gameArea.player.position.withRelative(Position3D.create(1, 1,0)))
                    InputType.Numpad1 -> gameArea.moveEntity(gameArea.player, gameArea.player.position.withRelative(Position3D.create(-1, 1,0)))
                }
            }*/


        }
    }

    private fun loadRexGameArea(gameArea: GameArea<Tile, GameBlock>) {
        var `is`: InputStream? = null
        try {
            `is` = FileInputStream(
                    File("d:\\Games\\roguelike\\REXPaint-v1.04\\images\\konsti00.xp"))
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        val rex = REXPaintResource.loadREXFile(`is`!!)

        val layers = rex.toLayerList(GameConfig.TILESET)
        val layer = layers[0]
        for (y in 0 until layer.height) {
            for (x in 0 until layer.width) {
                val p = Positions.create(x, y)
                val tile = layer.getAbsoluteTileAt(p)
                if(tile.isPresent) {
                    var t = tile.get()
                    if(t is CharacterTile) {
                        var gb = GameBlock.create(t)

                        //gb.defaultTile = tile.get()
                        //gb.blockingByTile = isBlocking(tile.get().asCharacterTile().get().character)
                        gameArea.setBlockAt(Positions.create3DPosition(p.x, p.y, 0), gb)
                    }
                }
            }
        }

    }

    private fun isBlocking(char: Char) : Boolean {
        Symbols.DOUBLE_LINE_VERTICAL
        if(char >= 0x2500.toChar() && char <= 0x2593.toChar()) {
            println("true" + char+" "+char.toByte().toInt().plus(127) + " "+char.toInt())
            return true
        }
        println("false"+char+" "+char.toByte().toInt().and(127)+" "+char.toInt())
        return false
    }

    private fun makeCaves(gameArea: GameArea<Tile, GameBlock>, smoothTimes: Int = 8) {
        val width = gameArea.actualSize().xLength
        val height = gameArea.actualSize().yLength
        var tiles: MutableMap<Position, Tile> = mutableMapOf()
        gameArea.actualSize().to2DSize().fetchPositions().forEach { pos ->
            tiles[pos] = if (Math.random() < 0.5) GameTileRepository.FLOOR else WALL
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

                            if (tiles[Positions.create(x + ox, y + oy)] === GameTileRepository.FLOOR)
                                floors++
                            else
                                rocks++
                        }
                    }
                    newTiles[Positions.create(x, y)] = if (floors >= rocks) GameTileRepository.FLOOR else WALL
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

