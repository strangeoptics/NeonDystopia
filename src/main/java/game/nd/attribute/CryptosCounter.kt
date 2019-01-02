package game.nd.attribute

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.expression.concatWithConvert
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

/**
 * Contains the amount of Zircons carried by an entity.
 */
data class CryptosCounter(internal val cryptosCountProperty: Property<Int> = createPropertyFrom(0)) : DisplayableAttribute {

    var cryptosCount: Int by cryptosCountProperty.asDelegate()

    override fun toComponent(width: Int): Component {
        val cryptosProp = createPropertyFrom("Cryptos: ")
                .concatWithConvert(cryptosCountProperty) { it.value.toString() }
        return Components.header()
                .withText(cryptosProp.value)
                .withSize(width, 1)
                .build().apply {
                    textProperty.bind(cryptosProp)
                }
    }
}
