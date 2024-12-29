package com.mucheng.mucute.bedrock.handler

import com.mucheng.mucute.bedrock.BedrockServerOptions
import com.mucheng.mucute.bedrock.packet.*
import com.mucheng.mucute.bedrock.packet.client.OpenConnectionRequest1Packet
import com.mucheng.mucute.bedrock.packet.client.OpenConnectionRequest2Packet
import com.mucheng.mucute.bedrock.packet.client.UnconnectedPingOpenConnectionPacket
import com.mucheng.mucute.bedrock.packet.client.UnconnectedPingPacket
import com.mucheng.mucute.bedrock.packet.server.OpenConnectionReply1Packet
import com.mucheng.mucute.bedrock.packet.server.OpenConnectionReply2Packet
import com.mucheng.mucute.bedrock.packet.server.UnconnectedPongPacket
import io.ktor.network.sockets.*

open class DefaultServerRakNetPacketHandler(
    private val bedrockServerOptions: BedrockServerOptions
) : RakNetPacketHandler {

    override suspend fun handle(
        datagramWriteChannel: DatagramWriteChannel,
        packet: RakNetPacket,
        address: InetSocketAddress
    ) {
        println(packet)
        when (packet) {
            is UnconnectedPingPacket -> {
                val unconnectedPongPacket = UnconnectedPongPacket(
                    packet.time,
                    bedrockServerOptions.serverGUID,
                    bedrockServerOptions.serverIDString
                )
                datagramWriteChannel.send(Datagram(unconnectedPongPacket.toSource(), address))
            }

            is UnconnectedPingOpenConnectionPacket -> {
                val unconnectedPongPacket = UnconnectedPongPacket(
                    packet.time,
                    bedrockServerOptions.serverGUID,
                    bedrockServerOptions.serverIDString
                )
                datagramWriteChannel.send(Datagram(unconnectedPongPacket.toSource(), address))
            }

            is OpenConnectionRequest1Packet -> {
                println("MTU: " + packet.MTU)
                val openConnectionReply1Packet = OpenConnectionReply1Packet(
                    serverGUID = bedrockServerOptions.serverGUID,
                    useSecurity = bedrockServerOptions.useSecurity,
                    cookie = bedrockServerOptions.cookie,
                    MTU = packet.MTU - 46
                )
                datagramWriteChannel.send(Datagram(openConnectionReply1Packet.toSource(), address))
            }

            is OpenConnectionRequest2Packet -> {
                val openConnectionReply2Packet = OpenConnectionReply2Packet(
                    serverGUID = bedrockServerOptions.serverGUID,
                    clientAddress = address,
                    MTU = packet.MTU,
                    encryptionEnabled = bedrockServerOptions.encryptionEnabled
                )
                datagramWriteChannel.send(Datagram(openConnectionReply2Packet.toSource(), address))
            }
            is FrameSetPacket -> {
                println(packet)
            }
        }
    }

}