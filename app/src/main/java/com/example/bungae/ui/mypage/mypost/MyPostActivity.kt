package com.example.bungae.ui.mypage.mypost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bungae.databinding.ActivityMypostBinding
import com.example.bungae.ui.mypage.adapter.MyPostAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyPostActivity : AppCompatActivity() {

    lateinit var binding: ActivityMypostBinding

    private val adapter by lazy { MyPostAdapter() }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val myPostViewModel by lazy {
        MyPostViewModel(auth, db)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMypostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerviewMypost.adapter = adapter
        binding.recyclerviewMypost.layoutManager = LinearLayoutManager(this)

        myPostViewModel.itemList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}