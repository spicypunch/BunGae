package com.example.bungae.singleton

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.tasks.await

object GetProfileImage {

    private var uri: Uri? = null

    suspend fun getProfileImage(uid: String) : Uri? {
        val imgRef = FireBaseAuth.imageStorage.reference.child("profile/image_${uid}.jpg")
        imgRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                uri = task.result
            }
        }.await()
        return uri
    }
}