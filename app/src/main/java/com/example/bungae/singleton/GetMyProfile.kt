package com.example.bungae.singleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bungae.data.ProfileData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object GetMyProfile {

    private var myProfile: ProfileData? = null
    suspend fun getMyProfile() : ProfileData? {
        FireBaseAuth.db.collection("Profile")
            .whereEqualTo("uid", FireBaseAuth.auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { result ->
                val item = result.toObjects(ProfileData::class.java)
                if (item.size != 0) {
                    myProfile = item.get(0)
                }
            }.await()
        return myProfile
    }
}