package com.example.bungae.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bungae.databinding.FragmentHomeBinding
import com.example.bungae.ui.home.adapter.HomeAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment() : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!
    private val adapter by lazy { HomeAdapter() }

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val homeViewModel by lazy {
        HomeViewModel(db)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerviewHome.adapter = adapter
        binding.recyclerviewHome.layoutManager = LinearLayoutManager(activity)

        homeViewModel.getFireStorage()

        homeViewModel.itemList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        homeViewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}