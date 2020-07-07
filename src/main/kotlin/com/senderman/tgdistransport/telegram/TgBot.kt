package com.senderman.tgdistransport.telegram

import com.google.inject.Inject
import com.google.inject.name.Named

class TgBot @Inject constructor(
        @Named("tgbot.chatId")
        private val chatId: Long,

        private val telegram: TelegramService
) {

    private val pictureFormats: HashSet<String> = hashSetOf("png", "jpg", "jpeg", "bmp")

    fun sendMessage(author: String, text: String) {
        val tts = "\uD83C\uDFAE<b>[DIS]</b> ${author.htmlSafe()}:\n${text.htmlSafe()}"
        telegram.sendMessage(chatId, tts).execute()
    }

    fun sendFile(author: String, fileUrl: String, caption: String = "") {
        val tts = "\uD83C\uDFAE<b>[DIS]</b> ${author.htmlSafe()}:\n${caption.htmlSafe()}"
        val extension = fileUrl.replace(Regex(".*?\\.(\\w+)\$"), "\$1")
        if (extension in pictureFormats) {
            telegram.sendPhoto(
                    chatId,
                    fileUrl,
                    tts
            ).execute()
        } else {
            telegram.sendDocument(
                    chatId,
                    fileUrl,
                    tts
            ).execute()
        }
    }

    private fun String.htmlSafe() = this.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
}