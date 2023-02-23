package com.example.bungae.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.FragmentHomeBinding
import com.example.bungae.ui.home.adapter.HomeRecyclerViewAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val adapter by lazy { HomeRecyclerViewAdapter() }

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("HomeHomeHomeHome", "HomeHomeHomeHome")
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)



        binding.recyclerviewHome.adapter = adapter
        binding.recyclerviewHome.layoutManager = LinearLayoutManager(activity)
//        adapter.updateList(list)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}