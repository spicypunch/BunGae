package com.example.bungae.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.database.ItemSample
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat

class PostViewModel(private val auth: FirebaseAuth, private var db: FirebaseFirestore, private var imageStorage: FirebaseStorage) : ViewModel() {

    private val currentTime: Long = System.currentTimeMillis()
    private val dateFormat = SimpleDateFormat("yy-MM-dd-E")

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    fun insertFireStorage(
        uId: String,
        title: String,
        content: String,
        category: String,
        address: String,
    ) {
        if (title.isBlank() || content.isBlank()) {
            _success.value = false
        } else {
            val itemSample = ItemSample(
                uid = uId,
                title = title,
                content = content,
                category = category,
                address = address,
                date = dateFormat.format(currentTime)
            )
            db.collection(auth.currentUser!!.uid).document().set(itemSample)
            _success.value = true
        }

    }


}