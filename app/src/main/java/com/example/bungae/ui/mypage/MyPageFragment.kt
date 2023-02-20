package com.example.bungae.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bungae.databinding.FragmentMypageBinding
import com.example.bungae.ui.account.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyPageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null

    private val binding get() = _binding!!

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val  myPageViewModel by lazy {
        MyPageViewModel(auth, db)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        myPageViewModel.getNickname()

        myPageViewModel.task.observe(viewLifecycleOwner, Observer {

            Glide.with(this).load(it).into(binding.imageMypageProfile)

        })

        binding.btnGetMyitem.setOnClickListener {
            startActivity(Intent(activity, MyPostActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}