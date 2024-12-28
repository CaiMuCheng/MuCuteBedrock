package com.mucheng.mucute.bedrock.packet.server.handler

import com.mucheng.mucute.bedrock.packet.BedrockPacketHandler
import com.mucheng.mucute.bedrock.packet.client.UnconnectedPingOpenConnectionPacket
import com.mucheng.mucute.bedrock.packet.client.UnconnectedPingPacket
import com.mucheng.mucute.bedrock.packet.server.ServerIDString
import com.mucheng.mucute.bedrock.packet.server.UnconnectedPongPacket
import io.ktor.network.sockets.*
import kotlinx.io.Source

open class UnconnectedPingOpenConnectionPacketHandler(
    private val serverGUID: Long,
    private val serverIDString: ServerIDString
) : BedrockPacketHandler {

    override suspend fun handle(datagramWriteChannel: DatagramWriteChannel, source: Source, address: SocketAddress) {
        val unconnectedPingOpenConnectionPacket = UnconnectedPingOpenConnectionPacket.fromSource(source)
        val unconnectedPongPacket = UnconnectedPongPacket(
            time = unconnectedPingOpenConnectionPacket.time,
            serverGUID = serverGUID,
            serverIDString = serverIDString
        )
        datagramWriteChannel.send(Datagram(unconnectedPongPacket.toSource(), address))
    }

}