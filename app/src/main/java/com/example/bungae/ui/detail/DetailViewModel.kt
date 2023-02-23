package com.example.bungae.ui.detail

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.database.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class DetailViewModel(
    private val db: FirebaseFirestore
) : ViewModel() {

    private var _profileList = MutableLiveData<Profile>()
    val profileList: LiveData<Profile>
        get() = _profileList

    private var _porfileImage = MutableLiveData<Uri>()
    val porfileImage: LiveData<Uri>
        get() = _porfileImage

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

    fun getProfileImage(user: String) {
        val imgRef = FirebaseStorage.getInstance().reference.child("profile/image_${user}.jpg")
        imgRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _porfileImage.value = task.result
            } else {

            }
        }
    }
}