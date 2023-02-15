package com.example.bungae.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.ItemHomeBinding

class HomeRecyclerViewAdapter() : RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder>(){

    private val itemList = mutableListOf<ItemSample>()

    fun updateList(items: MutableList<ItemSample>) {
        val diffCallback = HomeDiffUtilCallback(itemList, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        itemList.clear()
        itemList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(private val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root){
        val root = binding.root

//        fun bind(item: DataSample) {
//        }

        val tv_title = binding.tvHomeTitle
        val tv_address = binding.tvHomeAddress
        val tv_nick = binding.tvHomeNickname

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemHomeBinding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val homeData = itemList[position]
        holder.tv_title.text = homeData.title
        holder.tv_address.text = homeData.address
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}