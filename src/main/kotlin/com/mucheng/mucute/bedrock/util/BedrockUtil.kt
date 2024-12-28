package com.mucheng.mucute.bedrock.util

import com.mucheng.mucute.bedrock.BedrockServer
import com.mucheng.mucute.bedrock.packet.server.ServerIDString
import com.mucheng.mucute.bedrock.packet.server.handler.OpenConnectionRequest1PacketHandler
import com.mucheng.mucute.bedrock.packet.server.handler.OpenConnectionRequest2PacketHandler
import com.mucheng.mucute.bedrock.packet.server.handler.UnconnectedPingOpenConnectionPacketHandler
import com.mucheng.mucute.bedrock.packet.server.handler.UnconnectedPingPacketHandler
import io.ktor.network.sockets.*
import kotlin.random.Random

fun embeddedBedrockServer(
    socketAddress: InetSocketAddress = InetSocketAddress("0.0.0.0", 19132),
    serverGUID: Long = Random.nextLong(),
    useSecurity: Boolean = false,
    cookie: Int? = null,
    encryptionEnabled: Boolean = false,
    serverIDString: ServerIDString = ServerIDString(
        serverGUID = serverGUID,
        ipv4Port = socketAddress.port,
        ipv6Port = socketAddress.port
    )
): BedrockServer {
    return BedrockServer(socketAddress)
        .addPacketHandler(UnconnectedPingPacketHandler(serverGUID, serverIDString))
        .addPacketHandler(UnconnectedPingOpenConnectionPacketHandler(serverGUID, serverIDString))
        .addPacketHandler(OpenConnectionRequest1PacketHandler(serverGUID, useSecurity, cookie))
        .addPacketHandler(OpenConnectionRequest2PacketHandler(serverGUID, encryptionEnabled))
}