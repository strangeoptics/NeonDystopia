package game.nd.view

import game.nd.GameConfig
import game.nd.attribute.BlockOccupier
import game.nd.attribute.EntityPosition
import game.nd.attribute.EntityTile
import game.nd.attribute.Openable
import game.nd.attribute.type.Door
import game.nd.block.GameBlock
import game.nd.builder.EntityFactory
import game.nd.builder.GameBlockFactory
import game.nd.builder.GameTileRepository
import game.nd.events.GameLogEvent
import game.nd.extentions.*
import game.nd.view.fragments.PlayerStats
import game.nd.world.WorldImpl
import org.hexworks.cobalt.datatypes.extensions.map
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
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.onKeyStroke
import org.hexworks.zircon.api.kotlin.onMouseClicked
import org.hexworks.zircon.api.kotlin.onMouseMoved
import org.hexworks.zircon.api.mvc.base.BaseView
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.internal.Zircon
import sun.audio.AudioPlayer.player
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

class PlayView : BaseView() {

    private val logAreaHeight = 10
    private val VISIBLE_SIZE = Sizes.create3DSize(GameConfig.WINDOW_WIDTH-GameConfig.SIDEBAR_WIDTH, GameConfig.WINDOW_HEIGHT-logAreaHeight, 1)
    private val ACTUAL_SIZE = Sizes.create3DSize(200, 200, 2)

    var first = true

    override fun onDock() {
        if(!first) return

        first = false
        val gameArea = WorldImpl(VISIBLE_SIZE, ACTUAL_SIZE, this)

        val statsPanel = Components.panel()
                .withSize(Sizes.create(GameConfig.SIDEBAR_WIDTH, GameConfig.WINDOW_HEIGHT - logAreaHeight))
                //.withTitle("Stats")
                .withAlignmentWithin(screen, ComponentAlignment.TOP_LEFT)
                //.wrapWithBox()
                .build()
        statsPanel.addFragment(PlayerStats(gameArea.player).apply {

        })

        val logPanel = Components.panel()
                .withSize(Sizes.create(GameConfig.WINDOW_WIDTH / 2, logAreaHeight))
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
                .withSize(Sizes.create(GameConfig.WINDOW_WIDTH / 2, logAreaHeight))
                .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
                .wrapWithBox()
                .withTitle("Mouseview")
                .build()

        var lb0 = Components.label().withSize(GameConfig.WINDOW_WIDTH / 2, 1).build()
        var lb1 = Components.label().withPosition(0, 1).withSize(GameConfig.WINDOW_WIDTH / 2, 1).build()
        mousePanel.addComponent(lb0)
        mousePanel.addComponent(lb1)


        screen.addComponent(mousePanel)
        screen.addComponent(statsPanel)
        screen.addComponent(logPanel)

        loadRexGameArea(gameArea)
        loadRexGameAreaRails(gameArea)
        gameArea.addEntity(gameArea.player)
        gameArea.setBlockAt(Position3D.create(12, 40, 0), GameBlock.createWith(newGameEntityOfType(Door) {
            attributes(EntityTile(Tiles.newBuilder().buildCharacterTile().withCharacter(Symbols.WHITE_CIRCLE)),
                    EntityPosition(),
                    Openable(false))
        }))

        var citizen0 = EntityFactory.newCitizen(Position3D.create(40, 40, 0))
        gameArea.engine.addEntity(citizen0)
        gameArea.addEntity(citizen0)
        var citizen1 = EntityFactory.newCitizen(Position3D.create(25, 50, 0))
        gameArea.engine.addEntity(citizen1)
        gameArea.addEntity(citizen1)
        var citizen2 = EntityFactory.newCitizen(Position3D.create(18, 20, 0))
        gameArea.engine.addEntity(citizen2)
        gameArea.addEntity(citizen2)
        var citizen3 = EntityFactory.newCitizen(Position3D.create(9, 38, 0))
        gameArea.engine.addEntity(citizen3)
        gameArea.addEntity(citizen3)

        var hotelOwner = EntityFactory.newHotelOwner(Position3D.create(9, 31, 0))
        gameArea.engine.addEntity(hotelOwner)
        gameArea.addEntity(hotelOwner)


        var car0 = EntityFactory.newCar(Position3D.create(19, -5, 0), Position3D.create(19, 58, 0), Position3D.create(0, 1, 0), 2)
        gameArea.engine.addEntity(car0)
        gameArea.addEntityMultitile(car0)

        var car1 = EntityFactory.newCar(Position3D.create(24, 60, 0), Position3D.create(24, -5, 0), Position3D.create(0, -1, 0), 1)
        gameArea.engine.addEntity(car1)
        gameArea.addEntityMultitile(car1)


        gameArea.setBlockWithPosAt(Position3D.create(38, 30, 0), GameBlockFactory.stairsDown())
        gameArea.setBlockWithPosAt(Position3D.create(39, 30, 0), GameBlockFactory.stairsDown())

        gameArea.setBlockWithPosAt(Position3D.create(6, 14, 1), GameBlockFactory.stairsUp())
        gameArea.setBlockWithPosAt(Position3D.create(7, 14, 1), GameBlockFactory.stairsUp())

        gameArea.scrollForwardBy(12)

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
                block.withTopNonPlayerEntity { entity ->
                    logGameEvent("You see: ${entity.name}.")
                }
            }
        }
        screen.onMouseMoved {
            lb1.text = ""
            gameArea.withBlockAt(Position3D.from2DPosition(it.position.withRelativeX(-GameConfig.SIDEBAR_WIDTH).plus(gameArea.visibleOffset().to2DPosition()))) { block ->
                block.withTopNonPlayerEntity { entity ->
                    lb1.text = "You see: " + entity.name + " " + entity.tile.asCharacterTile().get().character
                }
            }

        }

        logGameEvent("You wake up. The room is depressing tiny and sterile. You remember ... a capsule hotel.")
    }

    private fun setREXPaintLayer(gameArea: WorldImpl, layer: Layer) {
        for (y in 0 until layer.height) {
            for (x in 0 until layer.width) {
                val p = Positions.create(x, y)
                val tile = layer.getAbsoluteTileAt(p)
                tile.map { t ->
                    if(t is CharacterTile) {
                        gameArea.setBlockWithPosAt(Positions.create3DPosition(p.x, p.y, 0), GameBlockFactory.fromDownTownMap(t))
                    }
                }
            }
        }
    }

    private fun loadRexGameArea(gameArea: WorldImpl) {
        val rex = REXPaintResource.loadREXFile(File("src/capsulhotel.xp").inputStream())
        val layers = rex.toLayerList(GameConfig.TILESET)
        setREXPaintLayer(gameArea, layers[0])

        var gb = gameArea.fetchBlockAt(Position3D.create(27, 22, 0)).get()
        var bgc = gb.defaultTile.foregroundColor
        var magenta = TileColors.create(240, 200, 0)
        gb.defaultTile = gb.defaultTile.withForegroundColor(bgc.lerp(magenta, 0.6f))

        gb = gameArea.fetchBlockAt(Position3D.create(28, 22, 0)).get()
        bgc = gb.defaultTile.foregroundColor
        magenta = TileColors.create(240, 200, 0)

        gb.defaultTile = gb.defaultTile.withForegroundColor(bgc.lerp(magenta, 0.6f))


        val rexCH = REXPaintResource.loadREXFile(File("src/capsulhotel01.xp").inputStream())
        val layersCH = rexCH.toLayerList(GameConfig.TILESET)
        val layerCH = layersCH[0]
        for (y in 0 until layerCH.height) {
            for (x in 0 until layerCH.width) {
                val p = Positions.create(7+x, 25+y)
                val tile = layerCH.getAbsoluteTileAt(Positions.create(x,y))
                tile.map { t ->
                    if(t is CharacterTile) {
                        gameArea.setBlockWithPosAt(Positions.create3DPosition(p.x, p.y, 0), GameBlockFactory.fromCapsulHotel(t))
                    }
                }
            }
        }
    }

    private fun loadRexGameAreaRails(gameArea: WorldImpl) {

        val rex = REXPaintResource.loadREXFile(File("src/Rails01.xp").inputStream())

        val layers = rex.toLayerList(GameConfig.TILESET)
        val layer = layers[0]
        for (y in 0 until layer.height) {
            for (x in 0 until layer.width) {
                val p = Positions.create(x, y)
                val tile = layer.getAbsoluteTileAt(p)
                tile.map { t ->
                    if(t is CharacterTile) {
                        gameArea.setBlockWithPosAt(Positions.create3DPosition(p.x, p.y, 1), GameBlock.create(t))
                    }
                }
            }
        }
    }
}
