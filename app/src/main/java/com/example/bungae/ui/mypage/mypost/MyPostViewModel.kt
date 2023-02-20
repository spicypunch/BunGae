package com.example.bungae.ui.mypage.mypost

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.database.ItemSample
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyPostViewModel(private val auth: FirebaseAuth,
    private val db: FirebaseFirestore) : ViewModel() {

    private val list: MutableList<ItemSample> = mutableListOf()

    private val _itemList = MutableLiveData<MutableList<ItemSample>>(mutableListOf())
    val itemList: LiveData<MutableList<ItemSample>>
        get() = _itemList

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun getMyPostList() {
        db.collection("ItemInfo")
            .whereEqualTo("uid", auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { results ->
//                list.clear()
                for (result in results) {
                    val item = result.toObject(ItemSample::class.java)
                    list.add(item)
                }

                list.sortByDescending { it.date }
                _itemList.value = list
            }
            .addOnFailureListener { e ->
                Log.e("Failed to get data", e.toString())
                _message.value = "데이"
            }
    }
}