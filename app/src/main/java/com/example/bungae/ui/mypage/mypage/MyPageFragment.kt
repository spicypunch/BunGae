package com.example.bungae.ui.mypage.mypage

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.bungae.R
import com.example.bungae.databinding.FragmentMypageBinding
import com.example.bungae.ui.account.login.LoginActivity
import com.example.bungae.ui.mypage.mypost.MyPostActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class MyPageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null

    private val binding get() = _binding!!

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val imageStorage: FirebaseStorage = Firebase.storage

    private var uriInfo: Uri? = null

    private val  myPageViewModel by lazy {
        MyPageViewModel(auth, db, imageStorage)
    }

    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_MEDIA_IMAGES
    )

    private val requestMultiplePermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            result.forEach {
                if (!it.value) {
                    Toast.makeText(context, "${it.key}권한 허용 필요", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    private val readImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.data?.let { uri ->
                activity?.contentResolver?.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                Glide.with(this).load(uri).into(binding.imageMypageProfile)
                uriInfo = uri
            }
        }

    private val getTakePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                uriInfo.let { Glide.with(this).load(uriInfo).into(binding.imageMypageProfile) }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        myPageViewModel.getNickname()

        binding.imageMypageProfile.setOnClickListener {
            requestMultiplePermission.launch(permissionList)
            openQuestionDialog()
            myPageViewModel.getProfileImage()
        }

        myPageViewModel.task.observe(viewLifecycleOwner, Observer {
            Glide.with(this).load(it).into(binding.imageMypageProfile)
        })

        myPageViewModel.loadImageSuccess.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "프로필 사진을 등록해주세요", Toast.LENGTH_SHORT).show()
        })

        myPageViewModel.lisProfile.observe(viewLifecycleOwner, Observer {
            binding.data = it
        })

        binding.btnGetMyitem.setOnClickListener {
            startActivity(Intent(activity, MyPostActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return root
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yy_MM_dd_HH_mm", Locale.KOREA).format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    private fun openQuestionDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("사진 수정")
        builder.setMessage("프로필 사진을 수정하시겠습니까?")
        builder.setNegativeButton("아니요", null)
        builder.setPositiveButton("네", object: DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                openLoadImageDialog(requireContext())
            }
        })
        builder.show()
    }

    private fun openLoadImageDialog(context: Context) {
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_choose_image, null)
        val dialogBuild = AlertDialog.Builder(context).apply {
            setView(dialogLayout)
        }
        val dialog = dialogBuild.create().apply { show() }

        val cameraAddBtn = dialogLayout.findViewById<Button>(R.id.btn_camera)
        val fileAddBtn = dialogLayout.findViewById<Button>(R.id.btn_file)

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"

        cameraAddBtn.setOnClickListener {
            uriInfo = createImageFile()
            getTakePicture.launch(uriInfo)
            openUpdateImageDialog()
            dialog.dismiss()
        }
        fileAddBtn.setOnClickListener {
            readImage.launch(intent)
            openUpdateImageDialog()
            dialog.dismiss()
        }
    }

    private fun openUpdateImageDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("사진 수정")
        builder.setMessage("선택한 사진으로 수정하시겠습니까?")
        builder.setNegativeButton("아니요", null)
        builder.setPositiveButton("네", object: DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                uriInfo?.let { myPageViewModel.updateImageToFirebase(it) }
            }
        })
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}