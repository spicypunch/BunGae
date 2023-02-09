package com.example.bungae.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bungae.databinding.ActivityLoginBinding
import com.example.bungae.ui.signup.SignUpActivity
import com.example.bungae.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    private var auth: FirebaseAuth? = FirebaseAuth.getInstance()
    private val viewModel by lazy {
        LoginViewModel(auth)
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

        viewModel.success.observe(this, Observer { it ->
            if (it) {
                Toast.makeText(baseContext, "로그인에 성공 하였습니다.", Toast.LENGTH_SHORT).show()
                moveHomeActivity(auth?.currentUser)
            } else {
                Toast.makeText(baseContext, "로그인에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
//    private fun signIn(email: String, passwd:String) {
//        auth?.signInWithEmailAndPassword(email, passwd)
//            ?.addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(baseContext, "로그인에 성공 하였습니다.", Toast.LENGTH_SHORT).show()
//                    moveHomeActivity(auth?.currentUser)
//                } else {
//                    Toast.makeText(baseContext, "로그인에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

    private fun moveHomeActivity(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}