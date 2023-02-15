package com.example.bungae.ui.post

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bungae.R
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.FragmentPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var imageStorage: FirebaseStorage = Firebase.storage
    private var uriInfo: Uri? = null

    private val postViewModel by lazy {
        PostViewModel(auth, db, imageStorage)
//        val postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
    }

//    private val permissionList = arrayOf(
//        Manifest.permission.CAMERA,
//        Manifest.permission.READ_MEDIA_IMAGES
//    )
//
//    private val requestMultiplePermission =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
//            result.forEach {
//                if (!it.value) {
//                    Toast.makeText(context, "${it.key}권한 허용 필요", Toast.LENGTH_SHORT).show()
//                }
//            }
//            openDialog()
//        }

//    private val readImage =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            it.data?.data?.let { uri ->
//                requireActivity().contentResolver.takePersistableUriPermission(uri,
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION
//                )
//                Glide.with(this).load(uri).into(binding.imgLoad)
//                uriInfo = uri
//            }
//        }
//
//    private val getTakePicture =
//        registerForActivityResult(ActivityResultContracts.TakePicture()) {
//            if (it) {
//                uriInfo.let { binding.imgLoad.setImageURI(uriInfo) }
//            }
//        }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmm ss", Locale.KOREA).format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root
//
//        binding.btnAddImage.setOnClickListener {
//            requestMultiplePermission.launch(permissionList)
//        }

        binding.btnCompletion.setOnClickListener {
            postViewModel.insertFireStorage(
                uId = auth.currentUser!!.uid,
                title = binding.editPostTitle.text.toString(),
                content = binding.editPostContent.text.toString(),
                category = binding.spinnerCategory.selectedItem.toString(),
                address = "경기도 성남시 분당구 야탑동 CGV",
            )
        }

        postViewModel.success.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it) {
                Toast.makeText(context, "게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "빈칸을 전부 채워주세요!", Toast.LENGTH_SHORT).show()
            }
        })


        return root
    }

//    private fun openDialog() {
//        val dialogLayout = layoutInflater.inflate(R.layout.dialog, null)
//        val dialogBuild = AlertDialog.Builder(context).apply {
//            setView(dialogLayout)
//        }
//        val dialog = dialogBuild.create().apply { show() }
//
//        val cameraAddBtn = dialogLayout.findViewById<Button>(R.id.btn_camera)
//        val fileAddBtn = dialogLayout.findViewById<Button>(R.id.btn_file)
//
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//        intent.type = "image/*"
//
//        cameraAddBtn.setOnClickListener {
//            uriInfo = createImageFile()
//            getTakePicture.launch(uriInfo)
//            dialog.dismiss()
//        }
//
//        fileAddBtn.setOnClickListener {
//            readImage.launch(intent)
//            dialog.dismiss()
//        }
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}