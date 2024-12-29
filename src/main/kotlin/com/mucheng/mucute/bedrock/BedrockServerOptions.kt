package com.mucheng.mucute.bedrock

import io.ktor.network.sockets.*
import kotlin.random.Random

class BedrockServerOptions private constructor(
    val address: InetSocketAddress,
    val serverGUID: Long,
    val serverIDString: ServerIDString,
    val useSecurity: Boolean,
    val cookie: Int?,
    val encryptionEnabled: Boolean
) {

    class Builder {

        private var address: InetSocketAddress = InetSocketAddress("0.0.0.0", 19132)

        private var serverGUID: Long = Random.nextLong()

        private var serverIDString: ServerIDString = ServerIDString(
            serverGUID = serverGUID,
            ipv4Port = address.port,
            ipv6Port = address.port
        )

        private var useSecurity: Boolean = false

        private var cookie: Int? = null

        private var encryptionEnabled: Boolean = false

        fun address(address: InetSocketAddress): Builder {
            this.address = address
            return this
        }

        fun serverGUID(serverGUID: Long): Builder {
            this.serverGUID = serverGUID
            return this
        }

        fun serverIDString(serverIDString: ServerIDString): Builder {
            this.serverIDString = serverIDString
            return this
        }

        fun useSecurity(useSecurity: Boolean): Builder {
            this.useSecurity = useSecurity
            return this
        }

        fun cookie(cookie: Int?): Builder {
            this.cookie = cookie
            return this
        }

        fun encryptionEnabled(encryptionEnabled: Boolean): Builder {
            this.encryptionEnabled = encryptionEnabled
            return this
        }

        fun build(): BedrockServerOptions {
            return BedrockServerOptions(address, serverGUID, serverIDString, useSecurity, cookie, encryptionEnabled)
        }

    }

}