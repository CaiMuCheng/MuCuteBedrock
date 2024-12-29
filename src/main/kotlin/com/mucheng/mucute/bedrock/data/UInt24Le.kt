package com.mucheng.mucute.bedrock.data

@JvmInline
value class UInt24Le(private val bytes: ByteArray) {

    init {
        require(bytes.size == 3) { "Byte array must be exactly 3 bytes long." }
    }

    constructor(value: UInt) : this(
        byteArrayOf(
            (value and 0xFFu).toByte(),
            ((value shr 8) and 0xFFu).toByte(),
            ((value shr 16) and 0xFFu).toByte()
        )
    )

    fun toUInt(): UInt {
        return (bytes[0].toUInt() and 0xFFu) or
                ((bytes[1].toUInt() and 0xFFu) shl 8) or
                ((bytes[2].toUInt() and 0xFFu) shl 16)
    }

    override fun toString(): String {
        return "UInt24Le(${toUInt()})"
    }

    fun toByteArray(): ByteArray {
        return bytes
    }

}