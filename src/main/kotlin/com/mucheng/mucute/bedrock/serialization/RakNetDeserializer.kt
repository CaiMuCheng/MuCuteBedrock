package com.mucheng.mucute.bedrock.serialization

import com.mucheng.mucute.bedrock.packet.RakNetPacket
import kotlinx.io.Source

interface RakNetDeserializer<T : RakNetPacket> {

    fun fromSource(source: Source): T

}