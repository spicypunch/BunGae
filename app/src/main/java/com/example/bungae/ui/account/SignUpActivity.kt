package com.example.bungae.ui.account

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bungae.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var binding: ActivitySignupBinding
    private val viewModel by lazy {
        SignUpViewModel(auth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            viewModel.checkPasswd(binding.editId.text.toString(), binding.editPw.text.toString(), binding.editPw2.text.toString())
        }

        viewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            if (it == "계정 생성을 완료했습니다.") {
                finish()
            }
        })
    }
}