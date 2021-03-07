package com.brijwel.mymusicplayer.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation

/**
 * Created by Brijwel on 07-03-2021.
 */
object BindingAdapter {
    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(imageView: ImageView, url: String) {
        imageView.load(url) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }
}