package org.alter.game.message.impl

import org.alter.game.message.Message

data class HintArrowMessage(val arrowType: Int, val indexOrX: Int, val arrowY:Int, val offsetZ: Int): Message