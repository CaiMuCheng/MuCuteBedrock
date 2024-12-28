package com.mucheng.mucute.bedrock.packet.server

import com.mucheng.mucute.bedrock.packet.BedrockPacket
import com.mucheng.mucute.bedrock.packet.Deserializer
import com.mucheng.mucute.bedrock.util.*
import io.ktor.utils.io.core.*
import kotlinx.io.Source

data class UnconnectedPongPacket(
    val time: Long,
    val serverGUID: Long,
    val serverIDString: ServerIDString
) : BedrockPacket(UNCONNECTED_PONG) {

    companion object : Deserializer<UnconnectedPongPacket> {

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