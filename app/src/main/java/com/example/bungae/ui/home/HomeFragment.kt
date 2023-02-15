package com.example.bungae.ui.home

import android.os.Bundle
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
    private var list: MutableList<ItemSample> = mutableListOf(ItemSample("test", "test", "test"))

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        list.add(ItemSample("test1", "test1", "test1"))
        list.add(ItemSample("test2", "test2", "test2"))
        list.add(ItemSample("test3", "test3", "test3"))
        list.add(ItemSample("test4", "test4", "test4"))

        binding.recyclerviewHome.adapter = adapter
        binding.recyclerviewHome.layoutManager = LinearLayoutManager(activity)
        adapter.updateList(list)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}