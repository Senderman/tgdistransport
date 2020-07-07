package com.senderman.tgdistransport

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.name.Names
import com.senderman.tgdistransport.discord.DiscordBot
import com.senderman.tgdistransport.telegram.TelegramService
import com.senderman.tgdistransport.telegram.TgBot
import retrofit2.Retrofit
import java.util.*
import kotlin.reflect.KClass


class TgDisTransportApplication(private val properties: Properties) : AbstractModule() {

    override fun configure() {

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.telegram.org/bot${properties.getProperty("tgbot.token")}/")
                .build()

        bind(TelegramService::class.java).toInstance(retrofit.create(TelegramService::class.java))
        bind(DiscordBot::class.java)
        bind(TgBot::class.java)
        String::class bindToString "tgbot.token"
        String::class bindToString "discordbot.token"
        Long::class bindToLong "tgbot.chatId"
    }

    private infix fun KClass<String>.bindToString(namedAs: String) {
        bind(this.java)
                .annotatedWith(Names.named(namedAs))
                .toInstance(properties.getProperty(namedAs))
    }

    private infix fun KClass<Long>.bindToLong(namedAs: String) {
        bind(this.java)
                .annotatedWith(Names.named(namedAs))
                .toInstance(properties.getProperty(namedAs).toLong())
    }

}

fun main() {
    val properties = ProfileProperty(System.getProperty("transport.profile"))
    val injector = Guice.createInjector(TgDisTransportApplication(properties))
    injector.getInstance(DiscordBot::class.java).block()
}
