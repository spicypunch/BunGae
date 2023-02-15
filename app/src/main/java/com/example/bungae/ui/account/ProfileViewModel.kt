package com.example.bungae.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileViewModel(private val auth: FirebaseAuth, private var db: FirebaseFirestore, private var imageStorage: FirebaseStorage) : ViewModel() {

    private var _checkNickname = MutableLiveData<Boolean>()
    val checkNickname: LiveData<Boolean>
        get() = _checkNickname

    fun checkNickName(nickName: String) {
        db.collection("Profile")
            .whereEqualTo("nickname", nickName)
            .get()
            .addOnSuccessListener {
                _checkNickname.value = true
            }
            .addOnFailureListener {
                _checkNickname.value = false
            }

    }
}