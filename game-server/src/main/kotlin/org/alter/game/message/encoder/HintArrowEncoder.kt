package org.alter.game.message.encoder

import org.alter.game.message.MessageEncoder
import org.alter.game.message.impl.HintArrowMessage
import java.lang.Exception

class HintArrowEncoder : MessageEncoder<HintArrowMessage>() {
    override fun extract(message: HintArrowMessage, key: String): Number = when(key) {
        "arrow_type" -> message.arrowType
        "index_or_x" -> message.indexOrX
        "arrow_y" -> message.arrowY
        "offset_z" -> message.offsetZ
        else -> throw Exception("Unhandled value key.")
    }

    override fun extractBytes(message: HintArrowMessage, key: String): ByteArray = throw Exception("Unhandled value key.")
}