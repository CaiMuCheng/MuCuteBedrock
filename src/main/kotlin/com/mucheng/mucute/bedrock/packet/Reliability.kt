package com.mucheng.mucute.bedrock.packet

enum class Reliability {

    Unreliable,
    UnreliableSequenced,
    Reliable,
    ReliableOrdered,
    ReliableSequenced;

    fun toByte(): Byte {
        return when (this) {
            Unreliable -> 0x00
            UnreliableSequenced -> 0x01
            Reliable -> 0x02
            ReliableOrdered -> 0x03
            ReliableSequenced -> 0x04
        }
    }

    fun isReliable(): Boolean {
        return when(this) {
            Reliable,
            ReliableOrdered,
            ReliableSequenced -> true
            else -> false
        }
    }

    fun isOrdered(): Boolean {
        return when(this) {
            UnreliableSequenced,
            ReliableOrdered,
            ReliableSequenced -> true

            else -> false
        }
    }

    fun isSequenced(): Boolean {
        return when (this) {
            UnreliableSequenced -> true
            ReliableSequenced -> true
            else -> false
        }
    }

    companion object {

        fun fromByte(byte: Byte): Reliability {
            return when (byte) {
                0x00.toByte() -> Unreliable
                0x01.toByte() -> UnreliableSequenced
                0x02.toByte() -> Reliable
                0x03.toByte() -> ReliableOrdered
                0x04.toByte() -> ReliableSequenced
                else -> throw IllegalStateException()
            }
        }

    }

}