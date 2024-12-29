package com.mucheng.mucute.bedrock.packet.server

import com.mucheng.mucute.bedrock.ServerIDString
import com.mucheng.mucute.bedrock.packet.RakNetPacket
import com.mucheng.mucute.bedrock.serialization.RakNetDeserializer
import com.mucheng.mucute.bedrock.util.*
import io.ktor.utils.io.core.*
import kotlinx.io.Source

data class UnconnectedPongPacket(
    val time: Long,
    val serverGUID: Long,
    val serverIDString: ServerIDString
) : RakNetPacket() {

    override val packetId = UNCONNECTED_PONG

    companion object Deserializer : RakNetDeserializer<UnconnectedPongPacket> {

        override fun fromSource(source: Source) = with(source) {
            checkPacketId(UNCONNECTED_PONG)
            val time = readLong()
            val serverGUID = readLong()
            checkMagic()
            val serverIDString = ServerIDString.fromRakNetString(readRakNetString())
            UnconnectedPongPacket(time, serverGUID, serverIDString)
        }

    }

    override fun toSource() = buildPacket {
        writePacketId()
        writeLong(time)
        writeLong(serverGUID)
        writeMagic()
        writeRakNetString(serverIDString.toRakNetString())
    }

}