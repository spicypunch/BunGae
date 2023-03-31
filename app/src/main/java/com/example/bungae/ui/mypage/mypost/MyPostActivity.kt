package com.example.bungae.ui.mypage.mypost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bungae.ui.home.adpater.PostListAdapter
import com.example.bungae.databinding.ActivityMypostBinding
import com.example.bungae.ui.mypage.PostListViewModel

class MyPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMypostBinding

    private val adapter by lazy { PostListAdapter() }

    private val postListViewModel by lazy {
        ViewModelProvider(this).get(PostListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMypostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerviewMypost.adapter = adapter
        binding.recyclerviewMypost.layoutManager = LinearLayoutManager(this)

        postListViewModel.getMyPostList()

        postListViewModel.itemList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}