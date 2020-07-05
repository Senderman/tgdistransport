package com.senderman.tgdistransport.telegram


import com.google.inject.Inject
import com.google.inject.name.Named
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.File

class TgBot @Inject constructor(
        @Named("tgbot.chatId")
        private val chatId: Long,

        @Named("tgbot.token")
        private val token: String,

        @Named("tgbot.username")
        private val username: String
) : TelegramLongPollingBot() {


    private val pictureFormats: HashSet<String> = hashSetOf("png", "jpg", "jpeg", "bmp")

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

    private fun String.htmlSafe() = this.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
}