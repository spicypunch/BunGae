package com.example.bungae.ui.mypage.mypage

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bungae.database.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MyPageViewModel(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private var imageStorage: FirebaseStorage
) : ViewModel() {

    private var _task = MutableLiveData<Uri>()
    val task: LiveData<Uri>
        get() = _task

    private var _loadImageSuccess = MutableLiveData<Boolean>(true)
    val loadImageSuccess: LiveData<Boolean>
        get() = _loadImageSuccess

    private var _listProfile = MutableLiveData<Profile>()
    val listProfile: LiveData<Profile>
        get() = _listProfile

    private var _checkNickname = MutableLiveData<Boolean>()
    val checkNickname: LiveData<Boolean>
        get() = _checkNickname

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
                    _listProfile.value = item.get(0)
                    getProfileImage()
                }

            }
    }

    fun getProfileImage() {
        val imgRef = FirebaseStorage.getInstance().reference.child("profile/image_${auth.currentUser!!.uid}.jpg")
        imgRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _task.value = task.result
            } else {
                _loadImageSuccess.value = false
            }
        }
    }

    fun updateImageToFirebase(uriInfo: Uri) {
        imageStorage = FirebaseStorage.getInstance()
        val fileName = "image_${auth.currentUser!!.uid}.jpg"
        val imageRef = imageStorage.reference.child("profile/").child(fileName)
        imageRef.putFile(uriInfo).addOnSuccessListener {

        }.addOnFailureListener {

        }
    }

    fun checkNickName(nickName: String) {
        db.collection("Profile")
            .whereEqualTo("nickname", nickName)
            .get()
            .addOnSuccessListener { results ->
                _checkNickname.value = results.documents.isEmpty()
            }
            .addOnFailureListener {

            }
    }

    fun updateNickName(nickName: String) {
        db.collection("Profile")
            .document(auth.currentUser!!.uid)
            .update("nickname", nickName)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }
}