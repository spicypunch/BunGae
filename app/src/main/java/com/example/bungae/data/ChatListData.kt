package com.example.bungae.data

data class ChatListData(
    val uid: String ,
    val senderNickname: String,
    val receiverNickname: String,
    val message: String,
    val timestamp: String
):java.io.Serializable