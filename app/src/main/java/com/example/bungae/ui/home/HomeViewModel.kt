package com.example.bungae.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.database.ItemSample
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class HomeViewModel(private val auth: FirebaseAuth, private var db: FirebaseFirestore, private var imageStorage: FirebaseStorage) : ViewModel() {
    private val _itemList = MutableLiveData<MutableCollection<ItemSample>>(mutableListOf())
    val itemList: LiveData<MutableCollection<ItemSample>>
        get() = _itemList

    fun getFireStorage() {
        db.collection("ItemInfo")
            .whereEqualTo("uid", auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
//                    Log.e("listcheck", "${document.data.values}")
                    _itemList.value = document.data.values
                }
            }
    }

}