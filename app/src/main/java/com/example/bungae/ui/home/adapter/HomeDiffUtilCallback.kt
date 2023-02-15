package com.example.bungae.ui.home.adapter

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.bungae.database.ItemSample

// firebase 적용 후 DiffUtil 파일 하나로 통일 예정
class HomeDiffUtilCallback(
    private val oldList: MutableList<ItemSample>,
    private val newList: MutableList<ItemSample>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].title == newList[newItemPosition].title

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}