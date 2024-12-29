package com.mucheng.mucute.bedrock.packet.client

import com.mucheng.mucute.bedrock.packet.RakNetPacket
import com.mucheng.mucute.bedrock.serialization.RakNetDeserializer
import com.mucheng.mucute.bedrock.util.*
import io.ktor.utils.io.core.*
import kotlinx.io.Source

@Suppress("PropertyName")
data class OpenConnectionRequest1Packet(
    val rakNetProtocolVersion: Int,
    val MTU: Int
) : RakNetPacket() {

    override val packetId = OPEN_CONNECTION_REQUEST_1

    companion object Deserializer : RakNetDeserializer<OpenConnectionRequest1Packet> {

        @Suppress("LocalVariableName")
        override fun fromSource(source: Source) = with(source) {
            checkPacketId(OPEN_CONNECTION_REQUEST_1)
            checkMagic()
            val protocolVersion = readByte().toInt()
            val MTU = readMTU()
            OpenConnectionRequest1Packet(protocolVersion, MTU)
        }

    }

    override fun toSource() = buildPacket {
        writePacketId()
        writeMagic()
        writeByte(rakNetProtocolVersion.toByte())
        writeMTU(MTU)
    }

}