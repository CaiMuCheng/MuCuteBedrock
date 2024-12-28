package com.mucheng.mucute.bedrock.packet.server.handler

import com.mucheng.mucute.bedrock.packet.BedrockPacketHandler
import com.mucheng.mucute.bedrock.packet.client.OpenConnectionRequest1Packet
import com.mucheng.mucute.bedrock.packet.server.OpenConnectionReply1Packet
import io.ktor.network.sockets.*
import kotlinx.io.Source

open class OpenConnectionRequest1PacketHandler(
    private val serverGUID: Long,
    private val useSecurity: Boolean,
    private val cookie: Int?
) : BedrockPacketHandler {

    override suspend fun handle(datagramWriteChannel: DatagramWriteChannel, source: Source, address: SocketAddress) {
        val openConnectionRequest1Packet = OpenConnectionRequest1Packet.fromSource(source)
        val openConnectionReply1Packet = OpenConnectionReply1Packet(
            serverGUID = serverGUID,
            useSecurity = useSecurity,
            cookie = cookie,
            MTU = openConnectionRequest1Packet.MTU
        )
        datagramWriteChannel.send(Datagram(openConnectionReply1Packet.toSource(), address))
    }

}