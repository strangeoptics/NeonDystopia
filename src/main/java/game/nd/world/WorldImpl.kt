package game.nd.world

import game.nd.attribute.EntityPosition
import game.nd.attribute.EntityTiles
import game.nd.attribute.Inventory
import game.nd.attribute.type.Item
import game.nd.block.GameBlock
import game.nd.builder.EntityFactory
import game.nd.extentions.*
import game.nd.view.PlayView
import org.hexworks.amethyst.api.Engines.newEngine
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.extensions.fold
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.screen.Screen
import kotlin.concurrent.thread

class WorldImpl(visibleSize: Size3D, actualSize: Size3D, val playView: PlayView) : GameArea<Tile, GameBlock> by GameAreaBuilder.newBuilder<Tile, GameBlock>()
        .withVisibleSize(visibleSize)
        .withActualSize(actualSize)
        .withDefaultBlock(GameBlock.create())
        .withLayersPerBlock(2)
        .build() {

    val engine = newEngine<GameContext>()
    val player = EntityFactory.newPlayer()


    init {
        player.position = Position3D.create(13,40,0)
        player.cryptoCounter.cryptosCount = 5
        var inventory = player.attribute<Inventory>()
        inventory.addItem(EntityFactory.newJacket())
        inventory.addItem(EntityFactory.newDagger())
        engine.addEntity(player)
    }

    fun setBlockWithPosAt(position: Position3D, block: GameBlock) {
        setBlockAt(position, block)
        //super<GameArea<Tile, GameBlock>>.setBlockAt(position, block)
        block.pos = position
    }

    fun printMap(pos: Position3D, width: Int, height: Int) {
        for(y in pos.y..pos.y+height-1) {
            for (x in pos.x..pos.x + width - 1) {
                var block = fetchBlockAt(Position3D.create(x,y,pos.z))
                block.map { b ->
                    if(b.isOccupied) {
                        b.withTopNonPlayerEntity { print(it.name[0]) }
                    } else {
                        print("*")
                    }
                }
            }
            println()
        }
    }
    fun addEntity(entity: GameEntity<EntityType>) {
        fetchBlockAt(entity.position).get().addEntity(entity)
    }
    /**
     * Adds the given [Entity] at the given [Position3D].
     * Has no effect if this world already contains the
     * given [Entity].
     */
    fun addEntity(entity: Entity<EntityType, GameContext>, position: Position3D) {
        engine.addEntity(entity)
        fetchBlockAt(position).map {
            it.addEntity(entity)
        }
        entity.position = position
    }

    fun removeEntity(entity: Entity<EntityType, GameContext>) {
        engine.removeEntity(entity)
        fetchBlockAt(entity.position).map { it.removeEntity(entity) }
        entity.position = Position3D.unknown()
    }

    fun addEntityMultitile(entity: GameEntity<EntityType>) {
        val pos = entity.attribute(EntityPosition::class).get().position
        var bb = entity.attribute(EntityTiles::class).get().bb
        for(y in pos.y..pos.y+bb.y-1) {
            for(x in pos.x..pos.x+bb.x-1) {
                fetchBlockAt(Position3D.create(x,y,pos.z)).map { it.addEntity(entity) }
            }
        }
    }

    fun moveEntityMultitile(entity: GameEntity<EntityType>, from: Position3D, to: Position3D): Boolean {
        //printMap(from, 3,3)
        val et = entity.attribute(EntityTiles::class).get()
        val delta = to.minus(from)
        for(y in from.y..from.y+et.bb.y-1) {
            for(x in from.x..from.x+et.bb.x-1) {
               //println(""+x + " "+ y)
               fetchBlockAt(Position3D.create(x,y,from.z)).map { block ->
                   block.removeEntity(entity)
               }

                val newPos = Position3D.create(x,y,from.z).plus(delta)
                //println("newPos: " + newPos)
                fetchBlockAt(newPos).map {
                    it.addEntity(entity)
                }

                //println(""+Position3D.create(x,y,from.z) + " -> " + newPos)
            }
            entity.position = to
        }
        return true
    }

    fun moveEntity(entity: GameEntity<EntityType>, from: Position3D, to: Position3D): Boolean {

        if(fetchBlockAt(to).isPresent && fetchBlockAt(from).isPresent) {
            var targetBlock = fetchBlockAt(to).get()
            if (targetBlock.blockingByTile) return false

            var block = fetchBlockAt(from)
            block.get().removeEntity(entity)

            fetchBlockAt(to).get().addEntity(entity)
            entity.position = to

            return true
        }
        return false
    }

    var firstChar: Char? = null

    fun update(screen: Screen, input: Input) {

        engine.update(GameContext(
                world = this,
                screen = screen,
                input = input,
                player = player))

    }

    fun whenHasBlockAt(pos: Position3D, fn: (GameBlock) -> Unit) {
        fetchBlockAt(pos).map(fn)
    }

    fun withBlockAt(position: Position3D, fn: (GameBlock) -> Unit) {
        fetchBlockAt(position).map(fn)
    }

    var t : Thread? = null
    var run = true
    fun toggleTimeModes(world: WorldImpl, screen: Screen) {
        if(t != null) {
            run = false
            t = null
        } else {
            run = true
            t = thread(start = true) {
                while (run) {

                    println("test")
                    update(screen, KeyStroke(character = ' ', type = InputType.Numpad5))
                    Thread.sleep(500)
                }
            }
        }
    }

    fun fetchEntitiesAt(pos: Position3D): List<GameEntity<EntityType>> {
        return fetchBlockAt(pos).fold(whenEmpty = { kotlin.collections.listOf() }, whenPresent = {
            it.entities.toList()
        })
    }

    fun findItemsAt(pos: Position3D): List<GameEntity<Item>> {
        return fetchEntitiesAt(pos).filterType()
    }
}