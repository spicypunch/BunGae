package com.example.bungae.ui.update

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.bungae.R
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.ActivityUpdatePostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.*

class UpdatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdatePostBinding
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var imageStorage: FirebaseStorage = Firebase.storage

    private var uriInfo: Uri? = null

    private val updatePostViewModel by lazy {
        UpdatePostViewModel(auth, db)
    }

    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_MEDIA_IMAGES
    )

    private val requestMultiplePermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            result.forEach {
                if (!it.value) {
                    Toast.makeText(this, "${it.key}권한 허용 필요", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            openDialog(this)
        }

    private val readImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.data?.let { uri ->
                contentResolver?.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                Glide.with(this).load(uri).into(binding.imgUpdateLoad)
                uriInfo = uri
            }
        }

    private val getTakePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                uriInfo.let { Glide.with(this).load(uriInfo).into(binding.imgUpdateLoad) }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra("item") as ItemSample

        updatePostViewModel.sendItem(item)

        binding.btnUpdateAddImage.setOnClickListener {
            requestMultiplePermission.launch(permissionList)
        }

        binding.btnUpdateCompletion.setOnClickListener {
            if (uriInfo == null) {
                updatePostViewModel.updateItem(
                    uriInfo = item.imageUrl,
                    title = binding.editUpdatePostTitle.text.toString(),
                    content = binding.editUpdatePostContent.text.toString(),
                    category = binding.spinnerUpdateCategory.selectedItem.toString(),
                    address = "서울시 강남구",
                    date = item.date
                )
            } else {
                updatePostViewModel.updateImageToFirebase(uriInfo!!, item.date)
            }
        }

        updatePostViewModel.imageUrl.observe(this, androidx.lifecycle.Observer {
            updatePostViewModel.updateItem(
                uriInfo = it.toString(),
                title = binding.editUpdatePostTitle.text.toString(),
                content = binding.editUpdatePostContent.text.toString(),
                category = binding.spinnerUpdateCategory.selectedItem.toString(),
                address = "서울시 강남구",
                date = item.date
            )
        })

        updatePostViewModel.item.observe(this, androidx.lifecycle.Observer {
            Glide.with(this).load(it.imageUrl).into(binding.imgUpdateLoad)
            binding.editUpdatePostTitle.text = Editable.Factory.getInstance().newEditable(it.title)
            binding.editUpdatePostContent.text =
                Editable.Factory.getInstance().newEditable(it.content)
        })

        updatePostViewModel.success.observe(this, androidx.lifecycle.Observer {
            if (it) {
                Toast.makeText(this, "게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "게시글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        updatePostViewModel.blankCheck.observe(this, androidx.lifecycle.Observer {
            if (!it) {
                Toast.makeText(this, "빈칸을 전부 채워주세요.", Toast.LENGTH_SHORT).show()
            }
        })

        updatePostViewModel.map.observe(this, androidx.lifecycle.Observer {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("result", it)
            })
            finish()
        })
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yy_MM_dd_HH_mm", Locale.KOREA).format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    private fun openDialog(context: Context) {
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
            dialog.dismiss()
        }
        fileAddBtn.setOnClickListener {
            readImage.launch(intent)
            dialog.dismiss()
        }
    }
}
