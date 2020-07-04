package com.senderman.tgdistransport

import com.senderman.tgdistransport.discord.DiscordBot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TgDisTransportApplication

fun main(args: Array<String>) {
    runApplication<TgDisTransportApplication>(*args).getBean(DiscordBot::class.java).block()
}
