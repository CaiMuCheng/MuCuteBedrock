package com.mucheng.mucute.bedrock.packet.server

import kotlin.random.Random

data class ServerIDString(
    val edition: String = "MCPE",
    val motd: String = "Dedicated Server",
    val protocolVersion: Int = 766,
    val version: String = "1.21.50",
    val playerCount: Int = 0,
    val maxPlayerCount: Int = 20,
    val serverGUID: Long = Random.nextLong(),
    val subMotd: String = "Bedrock level",
    val gameMode: String = "Survival",
    val gameModeNumeric: Int = 1,
    val ipv4Port: Int = 19132,
    val ipv6Port: Int = 19132
) {

    companion object {

        fun fromRakNetString(string: String): ServerIDString {
            val list = string.split(";")
            val edition = list[0]
            val motd = list[1]
            val protocolVersion = list[2].toInt()
            val version = list[3]
            val playerCount = list[4].toInt()
            val maxPlayerCount = list[5].toInt()
            val serverGUID = list[6].toLong()
            val subMotd = list[7]
            val gameMode = list[8]
            val gameModeNumeric = list[9].toInt()
            val ipv4Port = list[10].toInt()
            val ipv6Port = list[11].toInt()
            return ServerIDString(
                edition,
                motd,
                protocolVersion,
                version,
                playerCount,
                maxPlayerCount,
                serverGUID,
                subMotd,
                gameMode,
                gameModeNumeric,
                ipv4Port,
                ipv6Port
            )
        }

    }

    fun toRakNetString() = listOf<Any>(
        edition,
        motd,
        protocolVersion,
        version,
        playerCount,
        maxPlayerCount,
        serverGUID,
        subMotd,
        gameMode,
        gameModeNumeric,
        ipv4Port,
        ipv6Port
    ).joinToString(separator = ";", postfix = ";")

}