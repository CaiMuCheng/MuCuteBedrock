package com.mucheng.mucute.bedrock.packet.client

import com.mucheng.mucute.bedrock.packet.BedrockPacket
import com.mucheng.mucute.bedrock.packet.Deserializer
import com.mucheng.mucute.bedrock.util.checkMagic
import com.mucheng.mucute.bedrock.util.checkPacketId
import com.mucheng.mucute.bedrock.util.writeMagic
import io.ktor.utils.io.core.*
import kotlinx.io.Source

data class UnconnectedPingOpenConnectionPacket(val time: Long, val clientGUID: Long) : BedrockPacket(UNCONNECTED_PING_OPEN_CONNECTIONS) {

    companion object : Deserializer<UnconnectedPingOpenConnectionPacket> {

        override fun fromSource(source: Source) = with(source) {
            checkPacketId(UNCONNECTED_PING_OPEN_CONNECTIONS)
            val time = readLong()
            checkMagic()
            val clientGUID = readLong()
            UnconnectedPingOpenConnectionPacket(time, clientGUID)
        }

    }

    override fun toSource() = buildPacket {
        writePacketId()
        writeLong(time)
        writeMagic()
        writeLong(clientGUID)
        build()
    }

}