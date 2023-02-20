package com.example.bungae.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(private val auth: FirebaseAuth) : ViewModel() {

    private var _message = MutableLiveData<Boolean>()
    val message: LiveData<Boolean>
        get() = _message

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    init {
        _success.value = true
    }

    fun signIn(email: String, passwd:String) {
        if (email.isNotEmpty() && passwd.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, passwd)
                .addOnCompleteListener { task ->
                    _message.value = task.isSuccessful
                }
        } else {
            _success.value = false
        }
    }
}