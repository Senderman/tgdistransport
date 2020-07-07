package com.senderman.tgdistransport.telegram

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface TelegramService {

    @POST("sendMessage")
    fun sendMessage(
            @Query("chat_id") chatId: Long,
            @Query("text") text: String,
            @Query("parse_mode") parseMode: String = "HTML"
    ): Call<Unit>

    @POST("sendPhoto")
    fun sendPhoto(
            @Query("chat_id") chatId: Long,
            @Query("photo") photo: String,
            @Query("caption") caption: String? = null,
            @Query("parse_mode") parseMode: String = "HTML"
    ): Call<Unit>

    @POST("sendDocument")
    fun sendDocument(
            @Query("chat_id") chatId: Long,
            @Query("document") document: String,
            @Query("caption") caption: String? = null,
            @Query("parse_mode") parseMode: String = "HTML"
    ): Call<Unit>

}