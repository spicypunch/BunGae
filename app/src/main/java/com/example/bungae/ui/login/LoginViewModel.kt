package com.example.bungae.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(private val auth: FirebaseAuth?) : ViewModel() {

    private var _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun signIn(email: String, passwd:String) {
        if (email.isNotEmpty() && passwd.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, passwd)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _message.value = "로그인에 성공하였습니다."
                    } else {
                        _message.value = "로그인에 실패하였습니다."
                    }
                }
        } else {
            _message.value = "빈칸을 채워주세요!"
        }
    }
}