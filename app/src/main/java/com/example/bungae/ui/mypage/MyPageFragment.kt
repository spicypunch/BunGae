package com.example.bungae.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bungae.databinding.FragmentMypageBinding
import com.example.bungae.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MyPageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null

    private val binding get() = _binding!!

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myPageViewModel =
            ViewModelProvider(this).get(MyPageViewModel::class.java)

        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        val root: View = binding.root



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