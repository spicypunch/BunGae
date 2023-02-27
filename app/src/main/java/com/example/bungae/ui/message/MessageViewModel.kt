package com.example.bungae.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.data.ChatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MessageViewModel(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {

    private val list: MutableList<ChatModel> = mutableListOf()

    private var _chatList = MutableLiveData<MutableList<ChatModel>>()
    val chatList: LiveData<MutableList<ChatModel>>
        get() = _chatList

    fun getMyChatList() {
        db.collection("ChatRoom")
            .whereEqualTo("comemnts.comment.uid", auth.currentUser!!.uid)
            .orderBy("comemnts.comment.timestamp", Query.Direction.DESCENDING).limit(1)
    }

}