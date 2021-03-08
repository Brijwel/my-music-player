package com.brijwel.mymusicplayer.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.brijwel.mymusicplayer.R

/**
 * Created by Brijwel on 07-03-2021.
 */
object BindingAdapter {
    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(imageView: ImageView, url: String) {
        imageView.load(url) {
            crossfade(true)
            placeholder(R.drawable.default_art)
            transformations(CircleCropTransformation())
        }
    }
}