package com.example.bungae.adpater

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bungae.R
import com.example.bungae.data.ItemData
import com.example.bungae.databinding.ItemHomeBinding
import com.example.bungae.ui.detail.DetailActivity

class Adapter() : ListAdapter<ItemData, Adapter.MyViewHolder>(diffUtil){

    class MyViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        fun bind(item: ItemData) {
            binding.data = item

            when (binding.data!!.category) {
                "아무거나!" -> Glide.with(root).load(R.drawable.img_everything).into(binding.imageCategory)
                "카페 투어" -> Glide.with(root).load(R.drawable.img_coffee).into(binding.imageCategory)
                "운동" -> Glide.with(root).load(R.drawable.img_running).into(binding.imageCategory)
                "맛집 탐방" -> Glide.with(root).load(R.drawable.img_dinner).into(binding.imageCategory)
                "안주와 술" -> Glide.with(root).load(R.drawable.img_beer).into(binding.imageCategory)
                "영화" -> Glide.with(root).load(R.drawable.img_movie).into(binding.imageCategory)
            }

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