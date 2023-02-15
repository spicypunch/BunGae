package com.example.bungae.ui.post

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bungae.R
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.FragmentPostBinding
import com.example.bungae.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding
        get() = _binding!!

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var imageStorage: FirebaseStorage = Firebase.storage

    private var mainActivity: MainActivity? = null

    private val postViewModel by lazy {
        PostViewModel(auth, db, imageStorage)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = (activity as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnCompletion.setOnClickListener {
            postViewModel.insertFireStorage(
                title = binding.editPostTitle.text.toString(),
                content = binding.editPostContent.text.toString(),
                category = binding.spinnerCategory.selectedItem.toString(),
                address = "경기도 성남시 분당구 야탑동",
            )
        }

        postViewModel.success.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it) {
                Toast.makeText(context, "게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                mainActivity!!.replaceFragment()
            } else {
                Toast.makeText(context, "게시글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        postViewModel.blankCheck.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (!it) {
                Toast.makeText(context, "빈칸을 전부 채워주세요.", Toast.LENGTH_SHORT).show()
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mainActivity = null
    }
}