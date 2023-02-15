package com.example.bungae.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bungae.databinding.ActivityLoginBinding
import com.example.bungae.ui.signup.SignUpActivity
import com.example.bungae.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val viewModel by lazy {
        LoginViewModel(auth)
    }
    private var time: Long = 0

    private val callBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 1초 안에 두 번 누르면 앱 종료
            if (System.currentTimeMillis() > time + 1000) {
                time = System.currentTimeMillis()
                return
            }
            if (System.currentTimeMillis() <= time + 1000) {
                finishAffinity()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            viewModel.signIn(binding.editId.text.toString(), binding.editPw.text.toString())
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        viewModel.message.observe(this, Observer { it ->
            if (it) {
                Toast.makeText(baseContext, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(baseContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.success.observe(this, Observer {
            if (!it) {
                Toast.makeText(baseContext, "빈칸을 채워주세요!", Toast.LENGTH_SHORT).show()
            }
        })

        this.onBackPressedDispatcher.addCallback(this, callBack)
    }
}