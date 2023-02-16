package com.example.bungae.ui.account

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
        binding = ActivityWriteProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWriteProfileCheck.setOnClickListener {
            profileViewModel.checkNickName(binding.editWriteProfileNickname.text.toString())
        }

        binding.btnWriteProfileComplete.setOnClickListener {
            if (binding.spinnerAge.selectedItem.toString() == "남자") {
                profileViewModel.createProfile(binding.editWriteProfileNickname.text.toString(),
                    binding.editWriteProfileAge.text.toString().toInt(),
                    true)
            } else {
                profileViewModel.createProfile(binding.editWriteProfileNickname.text.toString(),
                    binding.editWriteProfileAge.text.toString().toInt(),
                    false)
            }
        }

        profileViewModel.checkNickname.observe(this, Observer {
            if (!it) {
                Toast.makeText(this, "중복된 값이 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "사용해도 되는 닉네임입니다.", Toast.LENGTH_SHORT).show()
            }
        })

        profileViewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        profileViewModel.checkFirestore.observe(this, Observer {
            if (it) {
                finish()
            }
        })

        this.onBackPressedDispatcher.addCallback(this, callBack)
    }
}