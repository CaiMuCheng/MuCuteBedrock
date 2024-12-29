package com.mucheng.mucute.bedrock

import com.mucheng.mucute.bedrock.serialization.RakNetDeserializer
import com.mucheng.mucute.bedrock.packet.RakNetPacket
import com.mucheng.mucute.bedrock.handler.RakNetPacketHandler
import com.mucheng.mucute.bedrock.handler.DefaultServerRakNetPacketHandler
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.network.address
import io.ktor.util.network.port
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.io.readByteArray

@Suppress("MemberVisibilityCanBePrivate")
open class BedrockServer(val options: BedrockServerOptions) {

    private val rakNetDeserializers = ArrayList<RakNetDeserializer<out RakNetPacket>>()

    private var rakNetPacketHandler: RakNetPacketHandler = DefaultServerRakNetPacketHandler(options)

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun listen() {
        val socket = aSocket(SelectorManager(Dispatchers.IO))
            .udp()
            .bind(options.address)

        while (true) {
            val datagram = socket.receive()
            val address = datagram.address.let {
                val javaAddress = it.toJavaAddress()
                InetSocketAddress(javaAddress.address, javaAddress.port)
            }
            datagram.packet.use { source ->
                for (deserializer in rakNetDeserializers) {
                    source.copy().use { copiedSource ->
                        var rakNetPacket: RakNetPacket? = null

                        try {
                            rakNetPacket = deserializer.fromSource(copiedSource)
                        } catch (e: Throwable) {
                            if (e is CancellationException) throw e
                        }

                        copiedSource.close()

                        rakNetPacket?.let { packet ->
                            try {
                                rakNetPacketHandler.handle(socket, packet, address)
                            } catch (e: Throwable) {
                                if (e is CancellationException) throw e
                            }
                            break
                        } ?: continue
                    }
                }
            }
        }
    }

    fun setRakNetPacketHandler(rakNetPacketHandler: RakNetPacketHandler): BedrockServer {
        this.rakNetPacketHandler = rakNetPacketHandler
        return this
    }

    fun getRakNetPacketHandler(): RakNetPacketHandler {
        return rakNetPacketHandler
    }

    fun addDeserializer(rakNetDeserializer: RakNetDeserializer<out RakNetPacket>): BedrockServer {
        rakNetDeserializers.add(rakNetDeserializer)
        return this
    }

    fun replaceDeserializer(rakNetDeserializer: RakNetDeserializer<out RakNetPacket>) {
        val index = rakNetDeserializers.indexOf(rakNetDeserializer).takeIf { it >= 0 } ?: return
        rakNetDeserializers.removeAt(index)
        if (index == rakNetDeserializers.size) {
            rakNetDeserializers.add(rakNetDeserializer)
        } else {
            rakNetDeserializers.add(index, rakNetDeserializer)
        }
    }

    fun removeDeserializer(rakNetDeserializer: RakNetDeserializer<out RakNetPacket>): BedrockServer {
        rakNetDeserializers.remove(rakNetDeserializer)
        return this
    }

}