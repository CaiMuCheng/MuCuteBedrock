package com.mucheng.mucute.bedrock.packet.server

import com.mucheng.mucute.bedrock.packet.RakNetPacket
import com.mucheng.mucute.bedrock.serialization.RakNetDeserializer
import com.mucheng.mucute.bedrock.util.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.core.*
import kotlinx.io.Source

@Suppress("PropertyName")
data class OpenConnectionReply2Packet(
    val serverGUID: Long,
    val clientAddress: InetSocketAddress,
    val MTU: Int,
    val encryptionEnabled: Boolean
) : RakNetPacket() {

    override val packetId = OPEN_CONNECTION_REPLY_2

    companion object Deserializer : RakNetDeserializer<OpenConnectionReply2Packet> {

        @Suppress("LocalVariableName")
        override fun fromSource(source: Source) = with(source) {
            checkPacketId(OPEN_CONNECTION_REPLY_2)
            checkMagic()
            val serverGUID = readLong()
            val clientAddress = readAddress()
            val MTU = readShort().toInt()
            val encryptionEnabled = readBoolean()
            OpenConnectionReply2Packet(serverGUID, clientAddress, MTU, encryptionEnabled)
        }

    }

    override fun toSource()  = buildPacket {
        writePacketId()
        writeMagic()
        writeLong(serverGUID)
        writeAddress(clientAddress)
        writeShort(MTU.toShort())
        writeBoolean(encryptionEnabled)
    }

}