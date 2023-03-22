package com.example.bungae.singleton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bungae.data.ProfileData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object GetMyProfile {
    private var _myProfile = MutableLiveData<ProfileData>()
    val myProfile: LiveData<ProfileData>
        get() = _myProfile
    fun getMyProfile() {
        FireBaseAuth.db.collection("Profile")
            .whereEqualTo("uid", FireBaseAuth.auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { result ->
                val item = result.toObjects(ProfileData::class.java)
                if (item.size != 0) {
                    _myProfile.value = item.get(0)
                }
            }
            .addOnFailureListener { e ->
            }
    }
}