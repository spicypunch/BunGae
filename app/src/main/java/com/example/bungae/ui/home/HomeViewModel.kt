package com.example.bungae.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.database.ItemSample
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage

class HomeViewModel(private val auth: FirebaseAuth, private var db: FirebaseFirestore, private var imageStorage: FirebaseStorage) : ViewModel() {

    private var list: MutableList<ItemSample> = mutableListOf()

    private val _itemList = MutableLiveData<MutableList<ItemSample>>(mutableListOf())
    val itemList: LiveData<MutableList<ItemSample>>
        get() = _itemList

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun getFireStorage() {
        db.collection("ItemInfo")
            .whereEqualTo("uid", auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val item = document.toObject(ItemSample::class.java)
                    list.add(item)
                }
                // 데이터를 불러올 때 저장한 순서대로 불러지지가 않아 재정렬 후 초기화
                list.sortByDescending { it.date }
                _itemList.value = list
            }
            .addOnFailureListener { e ->
                Log.e("Failed to get data", e.toString())
                _message.value = "데이터를 가져오는데 실패했습니다."
            }
    }

}