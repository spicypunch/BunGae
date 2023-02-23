package com.example.bungae.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bungae.database.MessageSample
import com.example.bungae.databinding.FragmentMessageBinding
import com.example.bungae.ui.message.adapter.MessageAdapter

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val adapter by lazy { MessageAdapter() }
    private val list: MutableList<MessageSample> = mutableListOf(MessageSample("김종민", "저 참여할게요!"))

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

        list.add(MessageSample("김다빈", "내일 시간 좀만 늦출 수 있을까요?"))
        list.add(MessageSample("흰돌이", "앤트맨 새로 나왔던데 그건 어때요?"))

        binding.recyclerviewMessage.adapter = adapter
        binding.recyclerviewMessage.layoutManager = LinearLayoutManager(activity)
        adapter.updateList(list)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}