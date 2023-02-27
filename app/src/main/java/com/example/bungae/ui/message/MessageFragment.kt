package com.example.bungae.ui.message

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bungae.data.ChatListData
import com.example.bungae.data.ChatModel
import com.example.bungae.databinding.FragmentMessageBinding
import com.example.bungae.ui.message.adapter.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val adapter by lazy { MessageAdapter() }
    private lateinit var map: Map<String, List<ChatModel>>
    private var list: MutableList<ChatListData> = mutableListOf()
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
//        adapter.submitList(list)

        messageViewModel.getMyChatList()

        messageViewModel.chatList.observe(viewLifecycleOwner, Observer {
            map = it.groupBy { it.comments.get("comment")!!.nickname }
            Log.e(
                "groupBy",
                map.toString()
            )

            for (i in map) {
                var cnt = 0
                Log.e(i.key, i.value.get(cnt).comments.get("comment")!!.timestamp)
                list.add(ChatListData(uid = i.value.get(cnt).comments.get("comment")!!.uid, nickname = i.key, message = i.value.get(cnt).comments.get("comment")!!.message, timestamp = i.value.get(cnt).comments.get("comment")!!.timestamp))
                cnt++
            }

//            Log.e(
//                "maxBy",
//                it.maxBy { it.comments.get("comment")!!.timestamp }.toString()
//            )
//            for (i in it) {
//                Log.e("chatlist4", i.comments.get("comment")!!.timestamp)
//            }

            adapter.submitList(list)

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}