package com.example.bungae.ui.mypage.mypage

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.data.ProfileData
import com.example.bungae.singleton.FireBaseAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MyPageViewModel : ViewModel() {

    private var _task = MutableLiveData<Uri>()
    val task: LiveData<Uri>
        get() = _task

    private var _loadImageSuccess = MutableLiveData<Boolean>(true)
    val loadImageSuccess: LiveData<Boolean>
        get() = _loadImageSuccess

    private var _listProfileData = MutableLiveData<ProfileData>()
    val listProfileData: LiveData<ProfileData>
        get() = _listProfileData

    private var _checkNickname = MutableLiveData<Boolean>()
    val checkNickname: LiveData<Boolean>
        get() = _checkNickname

    fun getNickname() {
        FireBaseAuth.db.collection("Profile")
            .whereEqualTo("uid", FireBaseAuth.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                val item = result.toObjects(ProfileData::class.java)

                if (item.size != 0) {
                    _listProfileData.value = item.get(0)
                    getProfileImage()
                }
            }
    }

    fun getProfileImage() {
        val imgRef = FireBaseAuth.imageStorage.reference.child("profile/image_${FireBaseAuth.auth.currentUser!!.uid}.jpg")
        imgRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _task.value = task.result
            } else {
                _loadImageSuccess.value = false
            }
        }
    }

    fun updateImageToFirebase(uriInfo: Uri) {
        val fileName = "image_${FireBaseAuth.auth.currentUser!!.uid}.jpg"
        val imageRef = FireBaseAuth.imageStorage.reference.child("profile/").child(fileName)
        imageRef.putFile(uriInfo).addOnSuccessListener {

        }.addOnFailureListener {

        }
    }

    fun checkNickName(nickName: String) {
        FireBaseAuth.db.collection("Profile")
            .whereEqualTo("nickname", nickName)
            .get()
            .addOnSuccessListener { results ->
                _checkNickname.value = results.documents.isEmpty()
            }
            .addOnFailureListener {

            }
    }

    fun updateNickName(nickName: String) {
        FireBaseAuth.db.collection("Profile")
            .document(FireBaseAuth.auth.currentUser!!.uid)
            .update("nickname", nickName)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }
}