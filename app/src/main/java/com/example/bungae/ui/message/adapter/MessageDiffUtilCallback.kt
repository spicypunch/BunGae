package com.example.bungae.ui.message.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.bungae.database.ItemSample
import com.example.bungae.database.MessageSample

// firebase 적용 후 DiffUtil 파일 하나로 통일 예정
class MessageDiffUtilCallback(
    private val oldList: MutableList<MessageSample>,
    private val newList: MutableList<MessageSample>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].nickname == newList[newItemPosition].nickname

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

}