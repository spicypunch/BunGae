package com.example.bungae.ui.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.database.ItemSample
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat

class PostViewModel(private val auth: FirebaseAuth, private var db: FirebaseFirestore, private var imageStorage: FirebaseStorage) : ViewModel() {

    private val currentTime: Long = System.currentTimeMillis()
    private val dateFormat = SimpleDateFormat("yy-MM-dd HH:mm")

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    private var _blankCheck = MutableLiveData<Boolean>()
    val blankCheck: LiveData<Boolean>
        get() = _blankCheck

    fun insertFireStorage(
        title: String,
        content: String,
        category: String,
        address: String,
    ) {
        if (title.isBlank() || content.isBlank()) {
            _blankCheck.value = false
        } else {
            val itemSample = ItemSample(
                uid = auth.currentUser!!.uid,
                title = title,
                content = content,
                category = category,
                address = address,
                date = dateFormat.format(currentTime)
            )

            val colRef: CollectionReference = db.collection("ItemInfo")
            val docRef: Task<DocumentReference> = colRef.add(itemSample)
            docRef.addOnSuccessListener { documentReference ->
                Log.d("게시글 등록 성공", "$documentReference")
                _success.value = true
            }
            docRef.addOnFailureListener {e ->
                Log.e("게시글 등록 실패", "e")
                _success.value = false
            }

//            db.collection("ItemInfo").document().set(itemSample)
//            _success.value = true
        }

    }


}