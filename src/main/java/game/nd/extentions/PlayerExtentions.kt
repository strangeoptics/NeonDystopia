package game.nd.extentions

import game.nd.attribute.CryptosCounter
import game.nd.attribute.type.Player

val GameEntity<Player>.cryptoCounter: CryptosCounter
    get() = attribute()