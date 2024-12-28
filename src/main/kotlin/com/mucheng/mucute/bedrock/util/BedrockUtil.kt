package com.mucheng.mucute.bedrock.util

import com.mucheng.mucute.bedrock.BedrockServer
import com.mucheng.mucute.bedrock.packet.server.ServerIDString
import com.mucheng.mucute.bedrock.packet.server.handler.UnconnectedPingPacketHandler
import io.ktor.network.sockets.*
import kotlin.random.Random

fun embeddedBedrockServer(
    socketAddress: InetSocketAddress = InetSocketAddress("0.0.0.0", 19132),
    serverGUID: Long = Random.nextLong(),
    serverIDString: ServerIDString = ServerIDString(
        serverGUID = serverGUID,
        ipv4Port = socketAddress.port,
        ipv6Port = socketAddress.port
    )
): BedrockServer {
    return BedrockServer(socketAddress)
        .addPacketHandler(UnconnectedPingPacketHandler(serverGUID, serverIDString))
}