package com.example.bungae.singleton

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object GetProfileImage {

    private var _profileImage = MutableLiveData<Uri>()
    val profileImgae: LiveData<Uri>
        get() = _profileImage

    //코투린 적용
    fun getProfileImage(uid: String){
        val imgRef = FireBaseAuth.imageStorage.reference.child("profile/image_${uid}.jpg")
        imgRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _profileImage.value = task.result
            } else {

            }
        }
    }
}