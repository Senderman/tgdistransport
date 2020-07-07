package com.senderman.tgdistransport.discord

import com.google.inject.Inject
import com.google.inject.name.Named
import com.senderman.tgdistransport.telegram.TgBot
import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent

class DiscordBot @Inject constructor(
        @Named("discordbot.token")
        private val token: String,

        private val tgBot: TgBot
) {

    private var gateway: GatewayDiscordClient

    init {
        val client = DiscordClient.create(token)
        gateway = client.login().block()!!
        registerHandlers()
    }

    fun block() = gateway.onDisconnect().block()

    private fun registerHandlers() {
        gateway.on(MessageCreateEvent::class.java).subscribe {
            val message = it.message
            val author = message.userData.username()
            val content = message.content
            if (message.attachments.isNotEmpty()) {
                message.attachments.forEach { att ->
                    tgBot.sendFile(author, att.url, content)
                }
            } else {
                tgBot.sendMessage(author, content)
            }
        }
    }

}