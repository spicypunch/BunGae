package com.example.bungae.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bungae.database.ItemSample
import com.example.bungae.databinding.FragmentPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null

    var auth: FirebaseAuth? = null
    var db: FirebaseFirestore? = null
    var imageStorage: FirebaseStorage? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val postViewModel =
            ViewModelProvider(this).get(PostViewModel::class.java)

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        imageStorage = Firebase.storage

        binding.btnCompletion.setOnClickListener {
            // Cloud Firestore test
            val itemSample = ItemSample()
            itemSample.uid = auth?.currentUser?.uid
            itemSample.title = binding.editPostTitle.text.toString()
            itemSample.address = "성남시 분당구"
            itemSample.nickname = "종미니"

            db?.collection(auth!!.currentUser!!.uid)?.document()?.set(itemSample)

        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}