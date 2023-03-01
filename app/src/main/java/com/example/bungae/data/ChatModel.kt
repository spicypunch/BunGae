package com.example.bungae.data

import android.provider.ContactsContract.CommonDataKinds.Nickname

class ChatModel(
    val users: HashMap<String, Boolean> = HashMap(),
    val comments: HashMap<String, Comment> = HashMap()
) {
    class Comment(
        val uid: String = "",
        val senderNickname: String = "",
        val receiverNickname1: String = "",
        val message: String = "",
        val timestamp: String = ""
    )
}