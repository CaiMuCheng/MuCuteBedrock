package com.mucheng.mucute.bedrock.packet.client

import com.mucheng.mucute.bedrock.packet.RakNetPacket
import com.mucheng.mucute.bedrock.serialization.RakNetDeserializer
import com.mucheng.mucute.bedrock.util.checkMagic
import com.mucheng.mucute.bedrock.util.checkPacketId
import com.mucheng.mucute.bedrock.util.writeMagic
import io.ktor.utils.io.core.*
import kotlinx.io.Source

data class UnconnectedPingPacket(val time: Long, val clientGUID: Long) : RakNetPacket() {

    override val packetId = UNCONNECTED_PING

    companion object Deserializer : RakNetDeserializer<UnconnectedPingPacket> {

        override fun fromSource(source: Source) = with(source) {
            checkPacketId(UNCONNECTED_PING)
            val time = readLong()
            checkMagic()
            val clientGUID = readLong()
            UnconnectedPingPacket(time, clientGUID)
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