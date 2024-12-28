package com.mucheng.mucute.bedrock.util

import io.ktor.utils.io.core.*
import kotlinx.io.*
import java.nio.ByteBuffer

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