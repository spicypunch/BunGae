package com.example.bungae.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bungae.data.ChatListData
import com.example.bungae.databinding.FragmentMessageBinding
import com.example.bungae.ui.message.adapter.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val adapter by lazy { MessageAdapter() }
    private val list: MutableList<ChatListData> = mutableListOf()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val binding
        get() = _binding!!

    private val messageViewModel by lazy {
        MessageViewModel(auth, db)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerviewMessage.adapter = adapter
        binding.recyclerviewMessage.layoutManager = LinearLayoutManager(activity)
        adapter.submitList(list)

        messageViewModel.getMyChatList()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}