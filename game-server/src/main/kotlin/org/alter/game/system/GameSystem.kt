package org.alter.game.system

import org.alter.game.message.Message
import org.alter.game.message.MessageHandler
import org.alter.game.model.World
import org.alter.game.model.entity.Client
import org.alter.game.service.GameService
import gg.rsmod.net.packet.GamePacket
import gg.rsmod.net.packet.GamePacketReader
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

/**
 * A [ServerSystem] responsible for decoding and encoding [Message]s from and
 * to the [Client.channel].
 *
 * @author Tom <rspsmods@gmail.com>
 */
class GameSystem(channel: Channel, val world: World, val client: Client, val service: GameService) : ServerSystem(channel) {
    private val logger = KotlinLogging.logger{}

    private val messages: BlockingQueue<MessageHandle> = ArrayBlockingQueue<MessageHandle>(service.maxMessagesPerCycle)

    override fun receiveMessage(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is GamePacket) {
            val decoder = service.messageDecoders.get(msg.opcode)
            if (decoder == null) {
                logger.warn("No decoder found for message $msg.")
                return
            }
            val handler = service.messageDecoders.getHandler(msg.opcode)
            if (handler == null) {
                logger.warn("No handler found for message $msg")
                return
            }
            val message = decoder.decode(msg.opcode, service.messageStructures.get(msg.opcode)!!, GamePacketReader(msg))
            messages.add(MessageHandle(message, handler, msg.opcode, msg.payload.readableBytes()))

            /*
             * Release the allocated buffer for the [GamePacket].
             */
            msg.payload.release()
        }
    }

    override fun terminate() {
        client.requestLogout()
        logger.info("User '{}' requested disconnection from channel {}.", client.displayname, channel)
    }

    fun handleMessages() {
        for (i in 0 until service.maxMessagesPerCycle) {
            val next = messages.poll() ?: break
            // @TODO Add debug logging
            next.handler.handle(client, world, next.message)
        }
    }

    fun write(message: Message) {
        channel.write(message)
    }

    fun flush() {
        if (channel.isActive) {
            channel.flush()
        }
    }

    fun close() {
        channel.disconnect()
    }

    private data class MessageHandle(val message: Message, val handler: MessageHandler<Message>, val opcode: Int, val length: Int)

}
