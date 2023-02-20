package com.example.bungae.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.FragmentHomeBinding
import com.example.bungae.ui.home.adapter.HomeRecyclerViewAdapter
import com.example.bungae.ui.home.adapter.OnItemLongClickListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class HomeFragment() : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!
    private val adapter by lazy { HomeRecyclerViewAdapter() }

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val homeViewModel by lazy {
        HomeViewModel(auth, db)
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
//            adapter.updateList(it)
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