package com.example.bungae.singleton

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.bungae.R

object BindingConversions {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadUrlImage(imageView: ImageView, uri: String?) {
        if (uri != null) {
            Glide.with(imageView.context)
                .load(uri)
                .error(R.drawable.image_landscape)
                .placeholder(R.drawable.img_loading)
                .into(imageView)
        }
    }

    @BindingAdapter("localImage")
    @JvmStatic
    fun loadLocalImage(imageView: ImageView, uri: String?) {
        if (uri != null) {
            Glide.with(imageView.context)
                .load(uri.toInt())
                .error(R.drawable.img_everything)
                .placeholder(R.drawable.img_loading)
                .into(imageView)
        }
    }
}
