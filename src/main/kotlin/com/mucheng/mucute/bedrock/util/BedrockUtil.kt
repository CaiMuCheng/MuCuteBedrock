package com.mucheng.mucute.bedrock.util

import com.mucheng.mucute.bedrock.BedrockServer
import com.mucheng.mucute.bedrock.BedrockServerOptions
import com.mucheng.mucute.bedrock.ServerIDString
import com.mucheng.mucute.bedrock.packet.FrameSetPacket
import com.mucheng.mucute.bedrock.packet.client.OpenConnectionRequest1Packet
import com.mucheng.mucute.bedrock.packet.client.OpenConnectionRequest2Packet
import com.mucheng.mucute.bedrock.packet.client.UnconnectedPingOpenConnectionPacket
import com.mucheng.mucute.bedrock.packet.client.UnconnectedPingPacket
import io.ktor.network.sockets.*
import kotlin.random.Random

fun embeddedBedrockServer(
    address: InetSocketAddress = InetSocketAddress("0.0.0.0", 19132),
    serverGUID: Long = Random.nextLong(),
    useSecurity: Boolean = false,
    cookie: Int? = null,
    encryptionEnabled: Boolean = false,
    serverIDString: ServerIDString = ServerIDString(
        serverGUID = serverGUID,
        ipv4Port = address.port,
        ipv6Port = address.port
    )
): BedrockServer {
    return BedrockServer(
        BedrockServerOptions.Builder()
            .address(address)
            .serverGUID(serverGUID)
            .serverIDString(serverIDString)
            .useSecurity(useSecurity)
            .cookie(cookie)
            .encryptionEnabled(encryptionEnabled)
            .build()
    ).installRakNetServerDeserializers()
}

fun BedrockServer.installRakNetServerDeserializers(): BedrockServer {
    addDeserializer(UnconnectedPingPacket.Deserializer)
    addDeserializer(UnconnectedPingOpenConnectionPacket.Deserializer)
    addDeserializer(OpenConnectionRequest1Packet.Deserializer)
    addDeserializer(OpenConnectionRequest2Packet.Deserializer)
    addDeserializer(FrameSetPacket.Deserializer)
    return this
}