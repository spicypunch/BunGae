package com.example.bungae.ui.message.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bungae.database.MessageSample
import com.example.bungae.databinding.ItemMessageBinding

class MessageAdapter() : RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    private val messageList = mutableListOf<MessageSample>()

    fun updateList(items: MutableList<MessageSample>) {
        val diffCallback = MessageDiffUtilCallback(messageList, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        messageList.clear()
        messageList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root){
        val root = binding.root

//        fun bind(item: DataSample) {
//        }

        val tv_message = binding.tvMessage

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemMessageBinding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val messageData = messageList[position]
        holder.tv_message.text = messageData.message
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}