package com.example.bungae.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.ItemHomeBinding

class MyPostAdapter() : androidx.recyclerview.widget.ListAdapter<ItemSample, MyPostAdapter.MyViewHolder>(diffUtil){

    class MyViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        fun bind(item: ItemSample) {
            binding.data = item

            itemView.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemHomeBinding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ItemSample>() {

            override fun areItemsTheSame(oldItem: ItemSample, newItem: ItemSample): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: ItemSample, newItem: ItemSample): Boolean {
                return oldItem == newItem
            }
        }
    }
}