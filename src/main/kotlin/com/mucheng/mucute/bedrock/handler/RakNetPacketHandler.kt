package com.mucheng.mucute.bedrock.handler

import com.mucheng.mucute.bedrock.packet.RakNetPacket
import io.ktor.network.sockets.*

interface RakNetPacketHandler {

    suspend fun handle(datagramWriteChannel: DatagramWriteChannel, packet: RakNetPacket, address: InetSocketAddress)

}