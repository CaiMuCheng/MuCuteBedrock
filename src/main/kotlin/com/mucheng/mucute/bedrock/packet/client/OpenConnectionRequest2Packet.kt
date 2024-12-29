package com.mucheng.mucute.bedrock.packet.client

import com.mucheng.mucute.bedrock.packet.RakNetPacket
import com.mucheng.mucute.bedrock.serialization.RakNetDeserializer
import com.mucheng.mucute.bedrock.util.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.core.*
import kotlinx.io.Source
import kotlinx.io.readByteArray

@Suppress("PropertyName")
data class OpenConnectionRequest2Packet(
    val serverAddress: InetSocketAddress,
    val cookie: Int?,
    val clientSupportSecurity: Boolean?,
    val MTU: Int,
    val clientGUID: Long
) : RakNetPacket() {

    override val packetId = OPEN_CONNECTION_REQUEST_2

    companion object Deserializer : RakNetDeserializer<OpenConnectionRequest2Packet> {

        @Suppress("LocalVariableName")
        override fun fromSource(source: Source) = with(source) {
            checkPacketId(OPEN_CONNECTION_REQUEST_2)
            checkMagic()
            val serverAddress = readAddress()
            var cookie: Int?
            var clientSupportSecurity: Boolean?
            val newSource = copy()
            var MTU: Int
            var clientGUID: Long
            try {
                cookie = newSource.readInt()
                clientSupportSecurity = newSource.readBoolean()
                MTU = newSource.readShort().toInt()
                clientGUID = newSource.readLong()
                if (newSource.readByteArray().isNotEmpty()) {
                    newSource.close()
                    throw IllegalArgumentException()
                }
                readByteArray()
            } catch (_: Exception) {
                cookie = null
                clientSupportSecurity = null
                MTU = readShort().toInt()
                clientGUID = readLong()
            }
            OpenConnectionRequest2Packet(serverAddress, cookie, clientSupportSecurity, MTU, clientGUID)
        }

    }

    override fun toSource() = buildPacket {
        writePacketId()
        writeMagic()
        writeAddress(serverAddress)
        cookie?.let { writeInt(it) }
        clientSupportSecurity?.let { writeBoolean(it) }
        writeShort(MTU.toShort())
        writeLong(clientGUID)
    }

}