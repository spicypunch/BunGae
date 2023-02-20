package com.example.bungae.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemSample

    private val detailViewModel by lazy {
        DetailViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        item = intent.getSerializableExtra("data") as ItemSample
        

        detailViewModel.getData()
    }
}