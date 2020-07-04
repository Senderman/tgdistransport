package com.senderman.tgdistransport.telegram

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.File

@Component
class TgBot(
        @Value("\${tgbot.chatId}")
        private val chatId: Long,

        @Value("\${tgbot.token}")
        private val token: String,

        @Value("\${tgbot.username}")
        private val username: String
) : TelegramLongPollingBot() {


    private val pictureFormats: Array<String> = arrayOf("png", "jpg", "jpeg", "bmp")

    override fun getBotUsername() = username

    override fun getBotToken() = token

    override fun onUpdateReceived(update: Update) {
        // NO need now
    }

    fun sendMessage(author: String, text: String) {
        val tts = "\uD83C\uDFAE<b>[DIS]</b> ${author.htmlSafe()}:\n${text.htmlSafe()}"
        execute(SendMessage(chatId, tts).enableHtml(true))
    }

    fun sendFile(author: String, file: File, caption: String = "") {
        val tts = "\uD83C\uDFAE<b>[DIS]</b> ${author.htmlSafe()}:\n${caption.htmlSafe()}"
        if (file.extension in pictureFormats) {
            execute(SendPhoto()
                    .setChatId(chatId)
                    .setCaption(tts)
                    .setPhoto(file)
                    .setParseMode(ParseMode.HTML))
        } else {
            execute(SendDocument()
                    .setChatId(chatId)
                    .setCaption(tts)
                    .setDocument(file)
                    .setParseMode(ParseMode.HTML))
        }
    }

    private fun String.htmlSafe() = this.replace("<", "&lt;").replace(">", "&gt;").replace("&", "&amp;")
}