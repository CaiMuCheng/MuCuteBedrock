package com.mucheng.mucute.bedrock.util

import io.ktor.network.sockets.*
import io.ktor.utils.io.core.*
import kotlinx.io.*
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress
import java.nio.ByteBuffer
import kotlin.experimental.and
import kotlin.experimental.inv

private val MagicByteArray = byteArrayOf(0, -1, -1, 0, -2, -2, -2, -2, -3, -3, -3, -3, 18, 52, 86, 120)

fun Sink.writePacketId(packetId: Byte) = writeByte(packetId)

fun Source.checkPacketId(expectPacketId: Byte) {
    if (expectPacketId != readByte()) {
        throw IllegalArgumentException()
    }
}

fun Sink.writeMagic() = writeByteBuffer(ByteBuffer.wrap(MagicByteArray))

fun Source.checkMagic() {
    if (!readByteArray(16).contentEquals(MagicByteArray)) {
        throw IllegalArgumentException()
    }
}

fun Sink.writeRakNetString(string: String) {
    writeUShort(string.length.toUShort())
    writeString(string)
}

fun Source.readRakNetString(): String {
    val length = readUShort()
    return readString(length.toLong())
}

@Suppress("LocalVariableName")
fun Sink.writeMTU(MTU: Int) {
    writeByteBuffer(ByteBuffer.wrap(ByteArray(MTU)))
}

fun Source.readMTU(): Int {
    return readByteArray().size
}

fun Sink.writeBoolean(boolean: Boolean) {
    writeByte(if (boolean) 0x01 else 0x00)
}

fun Source.readBoolean(): Boolean {
    val byte = readByte()
    if (byte == 0x01.toByte()) {
        return true
    }
    if (byte == 0x00.toByte()) {
        return false
    }
    throw IllegalArgumentException()
}

private const val AF_INET6 = 23

private fun flip(byteArray: ByteArray) {
    for (index in byteArray.indices) {
        byteArray[index] = byteArray[index].inv().and(0xFF.toByte())
    }
}

fun Sink.writeAddress(inetSocketAddress: InetSocketAddress) {
    val inetAddress = InetAddress.getByName(inetSocketAddress.hostname)
    val address = inetAddress.address.copyOf()
    when (inetAddress) {
        is Inet4Address -> {
            writeByte(0x04)
            flip(address)
            writeByteBuffer(ByteBuffer.wrap(address))
            writeUShort(inetSocketAddress.port.toUShort())
        }

        is Inet6Address -> {
            writeByte(0x06)
            writeShortLe(AF_INET6.toShort())
            writeUShort(inetSocketAddress.port.toUShort())
            writeInt(0)
            writeByteBuffer(ByteBuffer.wrap(address))
            writeInt(inetAddress.scopeId)
        }

        else -> {
            throw IllegalArgumentException()
        }
    }
}

fun Source.readAddress(): InetSocketAddress {
    val ipProtocolVersion = readByte()
    return when (ipProtocolVersion) {
        0x04.toByte() -> {
            val address = readByteArray(4)
            flip(address)
            val port = readUShort()
            InetSocketAddress(InetAddress.getByAddress(address).hostAddress, port.toInt())
        }

        0x06.toByte() -> {
            readShortLe()
            val port = readUShort()
            readInt()
            val address = readByteArray(16)
            val scopeId = readInt()
            InetSocketAddress(Inet6Address.getByAddress(null, address, scopeId).hostAddress, port.toInt())
        }

        else -> {
            throw IllegalArgumentException()
        }
    }
}