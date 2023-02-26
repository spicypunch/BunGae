package com.example.bungae.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bungae.data.ChatListData
import com.example.bungae.databinding.FragmentMessageBinding
import com.example.bungae.ui.message.adapter.MessageAdapter

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val adapter by lazy { MessageAdapter() }
    private val list: MutableList<ChatListData> = mutableListOf()

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val messageViewModel =
            ViewModelProvider(this).get(MessageViewModel::class.java)

        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        list.add(ChatListData(uid = "123", nickname = "123", message = "123", timeStamp = "2023-02-24"))

        binding.recyclerviewMessage.adapter = adapter
        binding.recyclerviewMessage.layoutManager = LinearLayoutManager(activity)
        adapter.submitList(list)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}