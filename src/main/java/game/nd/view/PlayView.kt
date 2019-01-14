package game.nd.view

import game.nd.GameConfig
import game.nd.block.GameBlock
import game.nd.builder.EntityFactory
import game.nd.builder.GameBlockFactory
import game.nd.builder.GameTileRepository
import game.nd.events.GameLogEvent
import game.nd.extentions.logGameEvent
import game.nd.extentions.position
import game.nd.view.fragments.PlayerStats
import game.nd.world.WorldImpl
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
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
import org.hexworks.zircon.api.kotlin.onMouseClicked
import org.hexworks.zircon.api.kotlin.onMouseMoved
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.internal.Zircon
import sun.audio.AudioPlayer.player
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

class PlayView(tileGrid: TileGrid) : BaseView(tileGrid) {

    private val screenSize = screen.size
    private val logAreaHeight = 10
    private val VISIBLE_SIZE = Sizes.create3DSize(GameConfig.WINDOW_WIDTH-GameConfig.SIDEBAR_WIDTH, GameConfig.WINDOW_HEIGHT-logAreaHeight, 1)
    private val ACTUAL_SIZE = Sizes.create3DSize(200, 200, 2)

    init {
        val gameArea = WorldImpl(VISIBLE_SIZE, ACTUAL_SIZE)

        val statsPanel = Components.panel()
                .withSize(Sizes.create(GameConfig.SIDEBAR_WIDTH, GameConfig.WINDOW_HEIGHT-logAreaHeight))
                //.withTitle("Stats")
                .withAlignmentWithin(screen, ComponentAlignment.TOP_LEFT)
                //.wrapWithBox()
                .build()
        statsPanel.addFragment(PlayerStats(gameArea.player).apply {

        })

        val logPanel = Components.panel()
                .withSize(Sizes.create(GameConfig.WINDOW_WIDTH/2, logAreaHeight))
                //.withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
                .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_LEFT)
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

        val mousePanel = Components.panel()
                .withSize(Sizes.create(GameConfig.WINDOW_WIDTH/2, logAreaHeight))
                .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
                .wrapWithBox()
                .withTitle("Mouseview")
                .build()

        var lb0 = Components.label().withSize(GameConfig.WINDOW_WIDTH/2,1).build()
        var lb1 = Components.label().withPosition(0,1).withSize(GameConfig.WINDOW_WIDTH/2,1).build()
        mousePanel.addComponent(lb0)
        mousePanel.addComponent(lb1)


        screen.addComponent(mousePanel)
        screen.addComponent(statsPanel)
        screen.addComponent(logPanel)

        loadRexGameArea(gameArea)
        loadRexGameAreaRails(gameArea)
        var block = gameArea.fetchBlockAt(gameArea.player.position)
        block.get().addEntity(gameArea.player)

        var citizen0 = EntityFactory.newCitizen()
        citizen0.position = Position3D.create(40,40, 0)
        gameArea.engine.addEntity(citizen0)
        gameArea.fetchBlockAt(citizen0.position).get().addEntity(citizen0)
        var citizen1 = EntityFactory.newCitizen()
        citizen1.position = Position3D.create(25,50, 0)
        gameArea.engine.addEntity(citizen1)
        gameArea.fetchBlockAt(citizen1.position).get().addEntity(citizen1)
        var citizen2 = EntityFactory.newCitizen()
        citizen2.position = Position3D.create(18,20, 0)
        gameArea.engine.addEntity(citizen2)
        gameArea.fetchBlockAt(citizen2.position).get().addEntity(citizen2)

        var car0 = EntityFactory.newCar()
        car0.position = Position3D.create(19,-5,0)
        gameArea.engine.addEntity(car0)
        gameArea.addEntityMultitile(car0, 3, 6)


        gameArea.setBlockWithPosAt(Position3D.create(38, 30, 0), GameBlockFactory.stairsDown())
        gameArea.setBlockWithPosAt(Position3D.create(39, 30, 0), GameBlockFactory.stairsDown())

        gameArea.setBlockWithPosAt(Position3D.create(6, 14, 1), GameBlockFactory.stairsUp())
        gameArea.setBlockWithPosAt(Position3D.create(7, 14, 1), GameBlockFactory.stairsUp())



        screen.addComponent(GameComponents.newGameComponentBuilder<Tile, GameBlock>()
                .withVisibleSize(VISIBLE_SIZE)
                .withGameArea(gameArea).withPosition(GameConfig.SIDEBAR_WIDTH, 0)
                .build())

        screen.onKeyStroke {
            gameArea.update(screen, it)
        }
        screen.onMouseClicked {
            println(it)
            gameArea.withBlockAt(Position3D.from2DPosition(it.position.withRelativeX(-GameConfig.SIDEBAR_WIDTH))) { block ->
                block.withTopNonPlayerEntity {
                    entity -> logGameEvent("You see: ${entity.name}.")
                }
            }
        }
        screen.onMouseMoved {
            var pos = it.position.withRelativeX(-GameConfig.SIDEBAR_WIDTH)
            println(pos)
            lb0.text = pos.toString()

            lb1.text = ""
            gameArea.withBlockAt(Position3D.from2DPosition(it.position.withRelativeX(-GameConfig.SIDEBAR_WIDTH))) { block ->
                block.withTopNonPlayerEntity {
                    entity -> lb1.text = "You see: " +entity.name
                }
            }

        }
    }

    private fun loadRexGameArea(gameArea: WorldImpl) {
        var `is`: InputStream? = null
        try {
            /*`is` = FileInputStream(
                    File("d:\\Games\\roguelike\\REXPaint-v1.04\\images\\konsti00.xp"))*/
            `is` = FileInputStream(
                    File("src/konsti00.xp"))
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
                        gameArea.setBlockWithPosAt(Positions.create3DPosition(p.x, p.y, 0), GameBlockFactory.fromDownTownMap(t))
                    }
                }
            }
        }

        var gb = gameArea.fetchBlockAt(Position3D.create(27, 22, 0)).get()
        var bgc = gb.defaultTile.foregroundColor
        var magenta = TileColors.create(240, 200, 0)
        gb.defaultTile = gb.defaultTile.withForegroundColor(lerp(bgc, magenta, 0.6f))

        gb = gameArea.fetchBlockAt(Position3D.create(28, 22, 0)).get()
        bgc = gb.defaultTile.foregroundColor
        magenta = TileColors.create(240, 200, 0)
        gb.defaultTile = gb.defaultTile.withForegroundColor(lerp(bgc, magenta, 0.6f))

    }

    fun lerp(a: TileColor, b: TileColor, t: Float): TileColor {
        return TileColor.create(
                (a.red + (b.red - a.red) * t).toInt(),
                (a.green + (b.green - a.green) * t).toInt(),
                (a.blue + (b.blue - a.blue) * t).toInt(),
                (a.alpha + (b.alpha - a.alpha) * t).toInt()
        )
    }

    private fun loadRexGameAreaRails(gameArea: WorldImpl) {
        var `is`: InputStream? = null
        try {
            /*`is` = FileInputStream(
                    File("d:\\Games\\roguelike\\REXPaint-v1.04\\images\\konsti00.xp"))*/
            `is` = FileInputStream(
                    File("src/Rails01.xp"))
                    //File("d:\\Games\\roguelike\\REXPaint-v1.04\\images\\Rails01.xp"))
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
                        gameArea.setBlockWithPosAt(Positions.create3DPosition(p.x, p.y, 1), GameBlock.create(t))
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

