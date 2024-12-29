package com.mucheng.mucute.bedrock.packet

import com.mucheng.mucute.bedrock.data.UInt24Le
import com.mucheng.mucute.bedrock.serialization.RakNetDeserializer
import com.mucheng.mucute.bedrock.util.readUInt24Le
import com.mucheng.mucute.bedrock.util.writeUInt24Le
import io.ktor.utils.io.core.*
import kotlinx.io.Source
import kotlinx.io.readByteArray
import kotlinx.io.readUShort
import kotlinx.io.writeUShort
import java.nio.ByteBuffer

data class FrameSetPacket(
    override val packetId: Byte,
    val sequenceNumber: UInt24Le,
    val flags: Byte,
    val lengthInBits: UShort,
    val reliableFrameIndex: UInt24Le,
    val sequencedFrameIndex: UInt24Le,
    val orderedFrameIndex: UInt24Le,
    val orderChannel: Byte,
    val compoundSize: Int,
    val compoundId: Short,
    val fragmentIndex: Int,
    val body: ByteArray
) : RakNetPacket() {

    companion object Deserializer : RakNetDeserializer<FrameSetPacket> {

        override fun fromSource(source: Source) = with(source) {
            val packetId = readByte()
            if (packetId !in FRAME_SET_BEGIN..FRAME_SET_END) {
                throw IllegalArgumentException()
            }
            val sequenceNumber = readUInt24Le()
            val flags = readByte()
            val lengthInBits = readUShort()
            val reliability = Reliability.fromByte(flags.toInt().and(224).shr(5).toByte())
            var reliableFrameIndex = UInt24Le(0U)
            var sequencedFrameIndex = UInt24Le(0U)
            var orderedFrameIndex = UInt24Le(0U)
            var orderChannel: Byte = 0x00
            var compoundSize = 0
            var compoundId: Short = 0
            var fragmentIndex = 0
            if (reliability.isReliable()) {
                reliableFrameIndex = readUInt24Le()
            }
            if (reliability.isSequenced()) {
                sequencedFrameIndex = readUInt24Le()
            }
            if (reliability.isOrdered()) {
                orderedFrameIndex = readUInt24Le()
                orderChannel = readByte()
            }
            if (flags.toInt().and(16) != 0) {
                compoundSize = readInt()
                compoundId = readShort()
                fragmentIndex = readInt()
            }
            val body = readByteArray()
            FrameSetPacket(
                packetId,
                sequenceNumber,
                flags,
                lengthInBits,
                reliableFrameIndex,
                sequencedFrameIndex,
                orderedFrameIndex,
                orderChannel,
                compoundSize,
                compoundId,
                fragmentIndex,
                body
            )
        }

    }

    override fun toSource() = buildPacket {
        writePacketId()
        writeUInt24Le(sequenceNumber)
        writeByte(flags)
        writeUShort(lengthInBits)
        if (isReliable()) {
            writeUInt24Le(reliableFrameIndex)
        }
        if (isSequenced()) {
            writeUInt24Le(sequencedFrameIndex)
        }
        if (isOrdered()) {
            writeUInt24Le(orderedFrameIndex)
            writeByte(orderChannel)
        }
        if (flags.toInt().and(16) != 0) {
            writeInt(compoundSize)
            writeShort(compoundId)
            writeInt(fragmentIndex)
        }
        writeByteBuffer(ByteBuffer.wrap(body))
    }

    fun isReliable(): Boolean {
        return reliability().isReliable()
    }

    fun isOrdered(): Boolean {
        return reliability().isOrdered()
    }

    fun isSequenced(): Boolean {
        return reliability().isSequenced()
    }

    fun reliability(): Reliability {
        return Reliability.fromByte(flags.toInt().and(224).shr(5).toByte())
    }

    fun isFragment(): Boolean {
        return flags.toInt().and(16) != 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FrameSetPacket

        if (packetId != other.packetId) return false
        if (flags != other.flags) return false
        if (orderChannel != other.orderChannel) return false
        if (sequenceNumber != other.sequenceNumber) return false
        if (lengthInBits != other.lengthInBits) return false
        if (reliableFrameIndex != other.reliableFrameIndex) return false
        if (sequencedFrameIndex != other.sequencedFrameIndex) return false
        if (orderedFrameIndex != other.orderedFrameIndex) return false
        if (compoundSize != other.compoundSize) return false
        if (compoundId != other.compoundId) return false
        if (fragmentIndex != other.fragmentIndex) return false
        if (!body.contentEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = packetId.toInt()
        result = 31 * result + flags
        result = 31 * result + orderChannel
        result = 31 * result + sequenceNumber.hashCode()
        result = 31 * result + lengthInBits.hashCode()
        result = 31 * result + reliableFrameIndex.hashCode()
        result = 31 * result + sequencedFrameIndex.hashCode()
        result = 31 * result + orderedFrameIndex.hashCode()
        result = 31 * result + compoundSize.hashCode()
        result = 31 * result + compoundId.hashCode()
        result = 31 * result + fragmentIndex.hashCode()
        result = 31 * result + body.contentHashCode()
        return result
    }

}