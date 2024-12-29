package com.mucheng.mucute.bedrock.packet.client

import com.mucheng.mucute.bedrock.packet.RakNetPacket
import com.mucheng.mucute.bedrock.serialization.RakNetDeserializer
import com.mucheng.mucute.bedrock.util.checkMagic
import com.mucheng.mucute.bedrock.util.checkPacketId
import com.mucheng.mucute.bedrock.util.writeMagic
import io.ktor.utils.io.core.*
import kotlinx.io.Source

data class UnconnectedPingOpenConnectionPacket(val time: Long, val clientGUID: Long) : RakNetPacket() {

    override val packetId = UNCONNECTED_PING_OPEN_CONNECTION

    companion object Deserializer : RakNetDeserializer<UnconnectedPingOpenConnectionPacket> {

        override fun fromSource(source: Source) = with(source) {
            checkPacketId(UNCONNECTED_PING_OPEN_CONNECTION)
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