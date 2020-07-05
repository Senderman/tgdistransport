package com.senderman.tgdistransport

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.name.Names
import com.senderman.tgdistransport.discord.DiscordBot
import com.senderman.tgdistransport.telegram.TgBot
import kotlin.reflect.KClass


class TgDisTransportApplication: AbstractModule(){

    private val propertiesLoader = PropertiesLoader(System.getProperty("transport.profile"))

    override fun configure() {
        bind(DiscordBot::class.java)
        bind(TgBot::class.java)
        String::class bindToString "tgbot.token"
        String::class bindToString "tgbot.username"
        String::class bindToString "discordbot.token"
        Long::class bindToLong "tgbot.chatId"
    }

    private infix fun KClass<String>.bindToString(namedAs: String){
        bind(this.java)
                .annotatedWith(Names.named(namedAs))
                .toInstance(propertiesLoader[namedAs])
    }

    private infix fun KClass<Long>.bindToLong(namedAs: String){
        bind(this.java)
                .annotatedWith(Names.named(namedAs))
                .toInstance(propertiesLoader[namedAs].toLong())
    }

}

fun main() {
    val injector = Guice.createInjector(TgDisTransportApplication())
    injector.getInstance(DiscordBot::class.java).block()
}
