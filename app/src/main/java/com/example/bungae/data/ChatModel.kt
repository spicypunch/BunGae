package com.example.bungae.data

class ChatModel(
    val users: HashMap<String, Boolean> = HashMap(),
    val comments: HashMap<String, Comment> = HashMap()
) {
    class Comment(
        val uid: String = "",
        val message: String = "",
        val timestamp: String = ""
    )
}