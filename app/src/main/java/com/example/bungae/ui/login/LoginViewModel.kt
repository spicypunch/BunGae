package com.example.bungae.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(private val auth: FirebaseAuth?) : ViewModel() {

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

//    private var _authResult = MutableLiveData<FirebaseAuth>()
//    val authResult: LiveData<FirebaseAuth>
//        get() = _authResult

    fun signIn(email: String, passwd:String) {
        auth?.signInWithEmailAndPassword(email, passwd)
            ?.addOnCompleteListener { task ->
                _success.value = task.isSuccessful
            }
    }
}