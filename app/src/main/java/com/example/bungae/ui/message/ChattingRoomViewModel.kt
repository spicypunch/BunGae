package com.example.bungae.ui.message

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bungae.data.ChatModel
import com.example.bungae.data.ItemData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat

class ChattingRoomViewModel(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore) {

    private val list: MutableList<ChatModel> = mutableListOf()

    private val uid = auth.currentUser!!.uid



    private var _chatData = MutableLiveData<MutableList<ChatModel>>()
    val chatData: LiveData<MutableList<ChatModel>>
        get() = _chatData


    fun setChatData(destinationUid: String, message: String){
        val currentTime: Long = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yy-MM-dd_HH:mm:ss")
        val comment = ChatModel.Comment(uid, message, dateFormat.format(currentTime))
        val chatModel = ChatModel()

        chatModel.users.put(uid, true)
        chatModel.users.put(destinationUid, true)
        chatModel.comments.put("comment", comment)

        db.collection("ChatRoom")
            .document()
            .set(chatModel)
            .addOnSuccessListener {
                Log.e("addOnSuccessListener", "메세지 보내기 성공")
            }
            .addOnFailureListener { e ->
                Log.e("addOnFailureListener", e.toString())
            }
    }

    fun getChatData(destinationUid: String) {
        db.collection("ChatRoom")
            .whereEqualTo("users.${auth.currentUser!!.uid}", true)
            .whereEqualTo("users.${destinationUid}", true)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Listen failed.", e.toString())
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    list.clear()
                    for (result in snapshot) {
                        val item = result.toObject(ChatModel::class.java)
                        list.add(item)
                    }
                    list.sortBy { it.comments.get("comment")!!.timestamp }
                    _chatData.value = list
                } else {
                    Log.e("Current data: null", "Current data: null")
                }
            }


    }
}