package com.mucheng.mucute.bedrock

import com.mucheng.mucute.bedrock.packet.BedrockPacketHandler
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Dispatchers

@Suppress("MemberVisibilityCanBePrivate")
open class BedrockServer(val socketAddress: InetSocketAddress) {

    private val packetHandlers = ArrayList<BedrockPacketHandler>()

    @OptIn(ExperimentalStdlibApi::class)
    suspend fun listen() {
        val socket = aSocket(SelectorManager(Dispatchers.IO))
            .udp()
            .bind(socketAddress)

        while (true) {
            val datagram = socket.receive()
            val source = datagram.packet
            val address = datagram.address

            source.copy().use {
                println("Received ID: 0x${it.readByte().toHexString()}")
            }

            for (packetHandler in packetHandlers) {
                val copiedSource = source.copy()
                val isSuccess = runCatching {
                    packetHandler.handle(socket, copiedSource, address)
                }.isSuccess

                copiedSource.close()

                if (isSuccess) {
                    break
                }
            }
            source.close()
        }
    }

    fun addPacketHandler(bedrockPacketHandler: BedrockPacketHandler): BedrockServer {
        packetHandlers.add(bedrockPacketHandler)
        return this
    }

    fun removePacketHandler(bedrockPacketHandler: BedrockPacketHandler): BedrockServer {
        packetHandlers.remove(bedrockPacketHandler)
        return this
    }

}