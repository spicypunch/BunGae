package com.example.bungae.ui.mypage

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.bungae.database.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MyPageViewModel(private val auth: FirebaseAuth, private var db: FirebaseFirestore) : ViewModel() {

    private var _task = MutableLiveData<Uri>()
    val task: LiveData<Uri>
        get() = _task

    private fun getNickname() {
        db.collection("Profile")
            .whereEqualTo("uid", auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                val profile = documents.toObjects(Profile::class.java)

            }
    }

    fun getProfileImage() {
        val imgRef = FirebaseStorage.getInstance().reference.child("profile/image_test.jpg")
        imgRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _task.value = task.result
            }
        }
    }
}