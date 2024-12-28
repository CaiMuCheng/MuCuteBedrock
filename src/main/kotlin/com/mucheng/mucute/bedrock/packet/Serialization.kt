package com.mucheng.mucute.bedrock.packet

import kotlinx.io.Source


interface Serializer {

    fun toSource(): Source

}

interface Deserializer<T : BedrockPacket> {

    fun fromSource(source: Source): T

}