package com.mucheng.mucute.bedrock.packet.server

import com.mucheng.mucute.bedrock.packet.RakNetPacket
import com.mucheng.mucute.bedrock.serialization.RakNetDeserializer
import com.mucheng.mucute.bedrock.util.*
import io.ktor.utils.io.core.*
import kotlinx.io.Source

@Suppress("PropertyName")
data class OpenConnectionReply1Packet(
    val serverGUID: Long,
    val useSecurity: Boolean,
    val cookie: Int?,
    val MTU: Int
) : RakNetPacket() {

    override val packetId = OPEN_CONNECTION_REPLY_1

    companion object Deserializer : RakNetDeserializer<OpenConnectionReply1Packet> {

        @Suppress("LocalVariableName")
        override fun fromSource(source: Source) = with(source) {
            checkPacketId(OPEN_CONNECTION_REPLY_1)
            checkMagic()
            val serverGUID = readLong()
            val useSecurity = readBoolean()
            var cookie: Int? = null
            if (useSecurity) {
                cookie = readInt()
            }
            val MTU = readShort().toInt()
            OpenConnectionReply1Packet(serverGUID, useSecurity, cookie, MTU)
        }

    }

    override fun toSource() = buildPacket {
        writePacketId()
        writeMagic()
        writeLong(serverGUID)
        writeBoolean(useSecurity)
        if (useSecurity) {
            writeInt(cookie ?: 0)
        }
        writeShort(MTU.toShort())
    }

}