package com.example.bungae.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.ActivityDetailBinding
import com.example.bungae.ui.update.UpdatePostActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemSample
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val detailViewModel by lazy {
        DetailViewModel( db)
    }

    private val getList: ActivityResultLauncher<ItemSample> =
        registerForActivityResult(ActivityContract()) { result: HashMap<String, String>? ->
            result?.let {
                binding.tvDetailTitle.text = it.get("title")
                binding.tvDetailContent.text = it.get("content")
                binding.tvDetailAddress.text = it.get("address")
                Glide.with(this).load(it.get("imageUrl")).into(binding.imageDetail)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        item = intent.getSerializableExtra("data") as ItemSample
        binding.itemData = item

        detailViewModel.getProfileData(item.uid)

        detailViewModel.getProfileImage(item.uid)

        detailViewModel.profileList.observe(this, Observer {
            binding.profileData = it
            if (item.uid != auth.currentUser!!.uid) {
                binding.btnDetailItemUpdate.visibility = View.INVISIBLE
                binding.btnDetailItemDelete.visibility = View.INVISIBLE
            } else {
                binding.btnSendMessage.visibility = View.INVISIBLE
            }
        })

        detailViewModel.porfileImage.observe(this, Observer {
            Glide.with(this).load(it).circleCrop().into(binding.imageDetailProfile)
        })

        binding.btnDetailItemUpdate.setOnClickListener {
//            startActivity(Intent(this, UpdatePostActivity::class.java))
            getList.launch(item)
        }
    }
}