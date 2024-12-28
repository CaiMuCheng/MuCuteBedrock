package com.mucheng.mucute.bedrock.packet

import com.mucheng.mucute.bedrock.util.writePacketId
import kotlinx.io.Sink

@Suppress("MemberVisibilityCanBePrivate")
abstract class BedrockPacket(
    val packetId: Byte
) : Serializer {

    companion object {
        const val UNCONNECTED_PING: Byte = 0x01
        const val UNCONNECTED_PING_OPEN_CONNECTIONS: Byte = 0x02
        const val UNCONNECTED_PONG: Byte = 0x1C
        const val OPEN_CONNECTION_REQUEST_1: Byte = 0x05
        const val OPEN_CONNECTION_REPLY_1: Byte = 0x06
        const val OPEN_CONNECTION_REQUEST_2: Byte = 0x07
        const val OPEN_CONNECTION_REPLY_2: Byte = 0x08
    }

    protected fun Sink.writePacketId() = writePacketId(packetId)

}