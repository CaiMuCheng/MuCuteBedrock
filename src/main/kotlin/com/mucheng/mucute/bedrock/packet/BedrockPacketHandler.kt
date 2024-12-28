package com.mucheng.mucute.bedrock.packet

import io.ktor.network.sockets.*
import kotlinx.io.Source

interface BedrockPacketHandler {

    suspend fun handle(datagramWriteChannel: DatagramWriteChannel, source: Source, address: SocketAddress)

}