package com.example.bungae.ui.message.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bungae.R
import com.example.bungae.data.ChatListData
import com.example.bungae.databinding.ItemMessageBinding
import com.example.bungae.ui.message.ChattingRoomActivity

class MessageAdapter() : ListAdapter<ChatListData, MessageAdapter.MyViewHolder>(diffUtil) {

    class MyViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root){
        val root = binding.root
        fun bind(item: ChatListData) {
            binding.tvMessageNickname.text = item.nickname
            binding.tvMessage.text = item.message
            Glide.with(itemView).load(R.drawable.ic_baseline_person_24).into(binding.imageProfile)

            itemView.setOnClickListener {
                root.context.startActivity(Intent(root.context, ChattingRoomActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemMessageBinding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatListData>() {

            override fun areItemsTheSame(oldItem: ChatListData, newItem: ChatListData): Boolean {
                return oldItem.timestamp == newItem.timestamp
            }

            override fun areContentsTheSame(oldItem: ChatListData, newItem: ChatListData): Boolean {
                return oldItem == newItem
            }
        }
    }
}