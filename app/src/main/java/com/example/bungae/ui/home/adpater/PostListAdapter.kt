package com.example.bungae.ui.home.adpater

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bungae.data.ItemData
import com.example.bungae.databinding.ItemHomeBinding
import com.example.bungae.ui.detail.DetailActivity

class PostListAdapter() : ListAdapter<ItemData, PostListAdapter.MyViewHolder>(diffUtil){

    class MyViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        fun bind(item: ItemData) {
            binding.data = item

            itemView.setOnClickListener {
                Intent(root.context, DetailActivity::class.java).apply {
                    putExtra("data", item)
                }.run { root.context.startActivity(this) }
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
        val diffUtil = object : DiffUtil.ItemCallback<ItemData>() {

            override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
                return oldItem == newItem
            }
        }
    }
}