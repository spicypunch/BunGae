package com.example.bungae.ui.update

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bungae.database.ItemData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class UpdatePostViewModel(
    private val auth: FirebaseAuth,
    private var db: FirebaseFirestore,
) {

    private var _item = MutableLiveData<ItemData>()
    val item: LiveData<ItemData>
        get() = _item

    private var _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = _imageUrl

    private var _blankCheck = MutableLiveData<Boolean>()
    val blankCheck: LiveData<Boolean>
        get() = _blankCheck

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    private var _map = MutableLiveData<HashMap<String, String>>()
    val map: LiveData<HashMap<String, String>>
        get() = _map

    fun sendItem(item: ItemData) {
        _item.value = item
    }

    fun updateImageToFirebase(uriInfo: Uri, date: String) {
        val fileName = "image_${auth.currentUser!!.uid}_${date}.jpg"
        val imageRef = FirebaseStorage.getInstance().reference.child("ItemInfo/").child(fileName)
        imageRef.putFile(uriInfo).addOnSuccessListener {
            getItemUrl(date)
        }.addOnFailureListener {

        }
    }

    private fun getItemUrl(date: String) {
        val imgRef = FirebaseStorage.getInstance().reference.child(
            "ItemInfo/image_${auth.currentUser!!.uid}_${date}.jpg"
        )
        imgRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _imageUrl.value = task.result.toString()
            } else {

            }
        }
    }

    fun updateItem(
        uriInfo: String,
        title: String,
        content: String,
        category: String,
        address: String,
        date: String
    ) {
        if (title.isBlank() || content.isBlank()) {
            _blankCheck.value = false
        } else {

            val map: HashMap<String, String> = hashMapOf(
                "imageUrl" to uriInfo,
                "title" to title,
                "content" to content,
                "category" to category,
                "address" to address
            )
            db.collection("ItemInfo")
                .document("${auth.currentUser!!.uid}_${date}")
                .update(map as Map<String, Any>)
                .addOnSuccessListener {
                    _success.value = true
                    _map.value = map
                }
                .addOnFailureListener {
                    _success.value = false
                }
        }
    }
}