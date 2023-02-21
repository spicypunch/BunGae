package com.example.bungae.ui.mypage.mypage

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.database.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MyPageViewModel(private val auth: FirebaseAuth, private val db: FirebaseFirestore) :
    ViewModel() {

    private var _task = MutableLiveData<Uri>()
    val task: LiveData<Uri>
        get() = _task

    private var _loadImageSuccess = MutableLiveData<Boolean>(true)
    val loadImageSuccess: LiveData<Boolean>
        get() = _loadImageSuccess

    private var _nickName = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickName

    fun getNickname() {
        db.collection("Profile")
            .whereEqualTo("uid", auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                val item = result.toObjects(Profile::class.java)

                /**
                 * 각 계정당 프로필 정보는 하나밖에 없기 때문에
                 * 이렇게 닉네임을 가져오는데
                 * 더 좋은 방법을 생각해보겠습니다.
                 */
                if (item.size != 0) {
                    Log.e("asdlf,.kjasdklf", item.get(0).nickname)
                    _nickName.value = item.get(0).nickname
                    getProfileImage(_nickName.value!!)
                }

            }
    }

    private fun getProfileImage(nickname: String) {
        val imgRef = FirebaseStorage.getInstance().reference.child("profile/image_${nickname}.jpg")
        imgRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _task.value = task.result
            } else {
                _loadImageSuccess.value = false
            }
        }
    }

    fun updateImageToFirebase() {

    }
}