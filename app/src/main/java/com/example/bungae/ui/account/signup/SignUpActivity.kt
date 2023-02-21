package com.example.bungae.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bungae.databinding.ActivitySignupBinding
import com.example.bungae.ui.account.profile.ProfileActivity
import com.example.bungae.ui.account.signup.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivitySignupBinding
    private val signUpActivity by lazy {
        SignUpViewModel(auth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            signUpActivity.checkPasswd(binding.editId.text.toString(), binding.editPw.text.toString(), binding.editPw2.text.toString())
        }

        signUpActivity.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            if (it == "계정 생성을 완료했습니다.") {
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
        })
    }
}