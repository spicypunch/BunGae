package com.example.bungae.ui.account.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bungae.databinding.ActivityLoginBinding
import com.example.bungae.ui.account.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    private val loginViewModel by lazy {
            ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    private var time: Long = 0

    private val callBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 1초 안에 두 번 누르면 앱 종료
            if (System.currentTimeMillis() > time + 1000) {
                time = System.currentTimeMillis()
                Toast.makeText(baseContext, "앱을 종료하려면 뒤로 버튼을 두 번 눌러주세요", Toast.LENGTH_SHORT).show()
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
            loginViewModel.signIn(binding.editId.text.toString(), binding.editPw.text.toString())
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        loginViewModel.message.observe(this, Observer { it ->
            if (it) {
                Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        loginViewModel.success.observe(this, Observer {
            if (!it) {
                Toast.makeText(this, "빈칸을 채워주세요!", Toast.LENGTH_SHORT).show()
            }
        })

        this.onBackPressedDispatcher.addCallback(this, callBack)
    }
}