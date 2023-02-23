package com.example.bungae.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.database.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailViewModel(
    private var db: FirebaseFirestore
) : ViewModel() {

    private var _profileList = MutableLiveData<Profile>()
    val profileList: LiveData<Profile>
        get() = _profileList

    fun getProfileData(user: String) {
        db.collection("Profile")
            .whereEqualTo("uid", user)
            .get()
            .addOnSuccessListener { result ->
                val item = result.toObjects(Profile::class.java)
                _profileList.value = item.get(0)
            }
            .addOnFailureListener { e ->
                Log.e("Failed to get ProfileData", e.toString())
            }
    }
}