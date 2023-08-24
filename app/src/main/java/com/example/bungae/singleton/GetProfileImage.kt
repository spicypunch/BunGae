package com.example.bungae.singleton

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

object GetProfileImage {

    suspend fun getProfileImage(uid: String): Uri? {
        return try {
            val imgRef = FirebaseStorage.getInstance().reference.child("profile/image_${uid}.jpg")
            val downloadUrl = imgRef.downloadUrl.await()
            downloadUrl
        } catch (e: Exception) {
            null
        }
    }
}