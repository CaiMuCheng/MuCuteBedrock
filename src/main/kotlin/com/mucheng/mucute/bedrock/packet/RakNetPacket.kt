package com.mucheng.mucute.bedrock.packet

import com.mucheng.mucute.bedrock.serialization.RakNetSerializer
import com.mucheng.mucute.bedrock.util.writePacketId
import kotlinx.io.Sink

@Suppress("MemberVisibilityCanBePrivate")
abstract class RakNetPacket(
) : RakNetSerializer {

    abstract val packetId: Byte

    companion object {
        const val UNCONNECTED_PING: Byte = 0x01
        const val UNCONNECTED_PING_OPEN_CONNECTION: Byte = 0x02
        const val UNCONNECTED_PONG: Byte = 0x1C
        const val OPEN_CONNECTION_REQUEST_1: Byte = 0x05
        const val OPEN_CONNECTION_REPLY_1: Byte = 0x06
        const val OPEN_CONNECTION_REQUEST_2: Byte = 0x07
        const val OPEN_CONNECTION_REPLY_2: Byte = 0x08
        const val FRAME_SET_BEGIN = 0x84.toByte()
        const val FRAME_SET_END = 0x8d.toByte()
    }

    protected fun Sink.writePacketId() = writePacketId(packetId)

}