package com.mucheng.mucute.bedrock.packet.server.handler

import com.mucheng.mucute.bedrock.packet.BedrockPacketHandler
import com.mucheng.mucute.bedrock.packet.client.OpenConnectionRequest2Packet
import com.mucheng.mucute.bedrock.packet.server.OpenConnectionReply2Packet
import io.ktor.network.sockets.*
import io.ktor.util.network.*
import kotlinx.io.Source

open class OpenConnectionRequest2PacketHandler(
    private val serverGUID: Long,
    private val encryptionEnabled: Boolean
) : BedrockPacketHandler {

    override suspend fun handle(datagramWriteChannel: DatagramWriteChannel, source: Source, address: SocketAddress) {
        val openConnectionRequest2Packet = OpenConnectionRequest2Packet.fromSource(source)
        val clientAddress = address.toJavaAddress().let { InetSocketAddress(it.address, it.port) }
        val openConnectionReply2Packet = OpenConnectionReply2Packet(
            serverGUID = serverGUID,
            clientAddress = clientAddress,
            MTU = openConnectionRequest2Packet.MTU,
            encryptionEnabled = encryptionEnabled
        )
        datagramWriteChannel.send(Datagram(openConnectionReply2Packet.toSource(), address))
    }

}