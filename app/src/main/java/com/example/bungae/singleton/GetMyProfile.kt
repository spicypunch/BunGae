package com.example.bungae.singleton

import com.example.bungae.data.ProfileData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object GetMyProfile {

    suspend fun getMyProfile(): ProfileData? {
        return try {
            val result = FirebaseFirestore.getInstance().collection("Profile")
                .whereEqualTo("uid", FirebaseAuth.getInstance().currentUser?.uid)
                .get()
                .await()
            val item = result.toObjects(ProfileData::class.java)
            if (item.isNotEmpty()) {
                item[0]
            } else {
                null
            }
        } catch (e: java.lang.Exception) {
            null
        }
    }
}