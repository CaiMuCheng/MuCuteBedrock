package com.mucheng.mucute.bedrock.packet

import com.mucheng.mucute.bedrock.util.writePacketId
import kotlinx.io.Sink

@Suppress("MemberVisibilityCanBePrivate")
abstract class BedrockPacket(
    val packetId: Byte
) : Serializer {

    companion object {
        const val UNCONNECTED_PING: Byte = 0x01
        const val UNCONNECTED_PONG: Byte = 0x1C
    }

    protected fun Sink.writePacketId() = writePacketId(packetId)

}