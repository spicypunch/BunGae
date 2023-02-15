package com.example.bungae.ui.account

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bungae.databinding.ActivityLoginBinding
import com.example.bungae.databinding.ActivityWriteProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityWriteProfileBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var imageStorage: FirebaseStorage = Firebase.storage
    private val profileViewModel by lazy {
        ProfileViewModel(auth, db, imageStorage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWriteProfileCheck.setOnClickListener {
            profileViewModel.checkNickName(binding.editWriteProfileNickname.text.toString())
        }

        profileViewModel.checkNickname.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "중복된 값이 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "사용해도 되는 닉네임입니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}