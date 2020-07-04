package com.senderman.tgdistransport.discord

import com.senderman.tgdistransport.telegram.TgBot
import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@Component
class DiscordBot(
        @Value("\${discordbot.token}") private val token: String,

        @Autowired
        private val tgBot: TgBot
) {

    private lateinit var gateway: GatewayDiscordClient

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
                    val file = downloadFile(att.url, att.filename)
                    tgBot.sendFile(author, file, content)
                    file.delete()
                }
            } else {
                tgBot.sendMessage(author, content)
            }
        }
    }

    private fun downloadFile(url: String, fileName: String): File {
        val connection = URL(url).openConnection()
        val input = connection.getInputStream()
        val file = File(fileName)
        val out = BufferedOutputStream(FileOutputStream(file))
        var length: Int
        val buffer = ByteArray(2048)
        while ((input.read(buffer).also { length = it }) != -1)
            out.write(buffer, 0, length)
        input.close()
        out.close()
        return file
    }

}