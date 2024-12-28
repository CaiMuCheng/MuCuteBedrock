package com.mucheng.mucute.bedrock.packet.server

import com.mucheng.mucute.bedrock.packet.BedrockPacket
import com.mucheng.mucute.bedrock.packet.Deserializer
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
) : BedrockPacket(OPEN_CONNECTION_REPLY_2) {

    companion object : Deserializer<OpenConnectionReply2Packet> {

        @Suppress("LocalVariableName")
        override fun fromSource(source: Source) = with(source) {
            checkPacketId(OPEN_CONNECTION_REPLY_2)
            checkMagic()
            val serverGUID = readLong()
            val clientAddress = readAddress()
            val MTU = readShort() - 46
            val encryptionEnabled = readBoolean()
            OpenConnectionReply2Packet(serverGUID, clientAddress, MTU, encryptionEnabled)
        }

    }

    override fun toSource()  = buildPacket {
        writePacketId()
        writeMagic()
        writeLong(serverGUID)
        writeAddress(clientAddress)
        writeShort((MTU + 46).toShort())
        writeBoolean(encryptionEnabled)
    }

}