package gg.rsmod.game.message

import gg.rsmod.game.message.impl.IgnoreMessage
import gg.rsmod.game.plugin.PluginRepository
import gg.rsmod.net.packet.DataOrder
import gg.rsmod.net.packet.DataSignature
import gg.rsmod.net.packet.DataTransformation
import gg.rsmod.net.packet.DataType
import gg.rsmod.net.packet.PacketType
import gg.rsmod.util.ServerProperties
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import java.io.File
import java.lang.IllegalStateException
import java.lang.NullPointerException
import java.util.ArrayList
import java.util.LinkedHashMap
import javax.xml.crypto.Data
import kotlin.collections.set

/**
 * Stores all the [MessageStructure]s that are used on the
 * [gg.rsmod.game.service.GameService].
 *
 * @author Tom <rspsmods@gmail.com>
 */
class MessageStructureSet {

    /**
     * The [MessageStructure]s stored respectively to their [Class].
     */
    private val structureClasses = Object2ObjectOpenHashMap<Class<*>, MessageStructure>()

    /**
     * The [MessageStructure]s stored respectively to their opcode.
     */
    private val structureOpcodes = arrayOfNulls<MessageStructure>(256)

    fun get(type: Class<*>): MessageStructure? = structureClasses[type]

    fun get(opcode: Int): MessageStructure? = structureOpcodes[opcode]

    /**
     * Decodes the [packetStructures] [File]. The format is irrelevant as long
     * as the [structureClasses] is populated with correct data.
     */
    fun load(packetStructures: File): MessageStructureSet {
        val properties = ServerProperties().loadYaml(packetStructures)
        load(properties, storeOpcodes = false)
        load(properties, storeOpcodes = true)
        return this
    }

    private fun load(properties: ServerProperties, storeOpcodes: Boolean) {
        val packets = properties.get<ArrayList<Any>>(if (storeOpcodes) "client-packets" else "server-packets")!!
        packets.forEach { packet ->
            val values = packet as LinkedHashMap<*, *>
            val className = values["message"] as String
            val packetType = if (values.containsKey("type")) PacketType.valueOf((values["type"] as String).toUpperCase()) else PacketType.FIXED
            val clazz = Class.forName(className)
            val packetLength = values["length"] as? Int ?: 0
            val ignore = values["ignore"] as? Boolean ?: false

            val packetOpcodes = mutableListOf<Int>()
            if (values.containsKey("opcodes")) {
                val split = (values["opcodes"] as String).trim().split(",")
                split.forEach { v -> packetOpcodes.add(v.toInt()) }
            } else if (values.containsKey("opcode")) {
                packetOpcodes.add(values["opcode"] as Int)
            }

            if (clazz::class.java != IgnoreMessage::class.java) {
                val packetStructure = if (values.containsKey("structure")) values["structure"] as ArrayList<*> else null
                val packetValues = Object2ObjectLinkedOpenHashMap<String, MessageValue>()
                packetStructure?.forEach { structure ->

                    val structValues = structure as LinkedHashMap<*, *>
                    if (!structValues.containsKey("write")) {
                        val name = structValues["name"] as String?
                        val order = if (structValues.containsKey("order")) DataOrder.valueOf(structValues["order"] as String) else DataOrder.BIG
                        val transform = if (structValues.containsKey("trans")) DataTransformation.valueOf(structValues["trans"] as String) else DataTransformation.NONE
                         val type = DataType.valueOf(structValues["type"] as String)
                         val signature = (if (structValues.containsKey("sign")) DataSignature.valueOf((structValues["sign"] as String).toUpperCase()) else DataSignature.SIGNED).also {
                                 packetValues[name] = MessageValue(id = name.toString(), order = order, transformation = transform, type = type, signature = it)
                             }
                    } else {
                        // Default values
                        val name = structValues["name"] as String
                        val write = structValues["write"] as String
                        var order = DataOrder.BIG
                        var transform = DataTransformation.NONE
                        var type:DataType? = null
                        var signature = DataSignature.SIGNED
                        /**
                         * Will be removed
                         *
                         */
                        when (write.lowercase()) {
                            "byte" -> {
                                type = DataType.BYTE
                                signature = DataSignature.UNSIGNED
                            }
                            "short" -> {
                                type = DataType.SHORT
                                signature = DataSignature.UNSIGNED
                            }
                            "medium" -> {
                                type = DataType.MEDIUM
                                signature = DataSignature.UNSIGNED
                            }
                            "int" -> {
                                type = DataType.INT
                                signature = DataSignature.UNSIGNED
                            }
                            "string" -> {
                                type = DataType.STRING
                                signature = DataSignature.UNSIGNED
                            }
                            "bytes" -> {
                                type = DataType.BYTES
                                signature = DataSignature.UNSIGNED
                            }
                            "byteadd" -> {
                                type = DataType.BYTE
                                transform = DataTransformation.ADD
                                signature = DataSignature.UNSIGNED
                            }
                            "byteneg" -> {
                                type = DataType.BYTE
                                transform = DataTransformation.NEGATE
                                signature = DataSignature.UNSIGNED
                            }
                            "bytesub" -> {
                                type = DataType.BYTE
                                transform = DataTransformation.SUBTRACT
                                signature = DataSignature.UNSIGNED
                            }
                            "shortle" -> {
                                type = DataType.SHORT
                                order = DataOrder.LITTLE
                                signature = DataSignature.UNSIGNED
                            }
                            "shortadd" -> {
                                type = DataType.SHORT
                                transform = DataTransformation.ADD
                                signature = DataSignature.UNSIGNED
                            }
                            "shortaddle" -> {
                                type = DataType.SHORT
                                transform = DataTransformation.ADD
                                order = DataOrder.LITTLE
                                signature = DataSignature.UNSIGNED
                            }
                            "medium1" -> {
                                type = DataType.MEDIUM
                                order = DataOrder.INVERSE_MIDDLE
                                signature = DataSignature.UNSIGNED
                            }
                            "intle" -> {
                                type = DataType.INT
                                order = DataOrder.LITTLE
                                signature = DataSignature.UNSIGNED
                            }
                            "intime" -> {
                                type = DataType.INT
                                order = DataOrder.INVERSE_MIDDLE
                                signature = DataSignature.UNSIGNED
                            }
                            "intme" -> {
                                type = DataType.INT
                                order = DataOrder.MIDDLE
                                signature = DataSignature.UNSIGNED
                            }
                            else -> {
                                PluginRepository.logger.warn("$write is unknown")
                            }
                        }
                        if (type == null) {
                            throw Exception("$name type = null")
                        }
                        packetValues[name] = MessageValue(id = name, order = order, transformation = transform, type = type,
                            signature = signature)
                    }
                }

                val messageStructure = MessageStructure(type = packetType, opcodes = packetOpcodes.toIntArray(), length = packetLength,
                        ignore = ignore, values = packetValues)
                structureClasses[clazz] = messageStructure
                if (storeOpcodes) {
                    packetOpcodes.forEach { opcode -> structureOpcodes[opcode] = messageStructure }
                }


            } else {
                val messageStructure = MessageStructure(type = packetType, opcodes = packetOpcodes.toIntArray(), length = packetLength,
                        ignore = ignore, values = Object2ObjectLinkedOpenHashMap(0))
                structureClasses[clazz] = messageStructure
                if (storeOpcodes) {
                    packetOpcodes.forEach { opcode -> structureOpcodes[opcode] = messageStructure }
                }
            }
        }
    }
}