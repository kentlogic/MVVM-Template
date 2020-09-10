package com.kentlogic.mvvm_template.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.kentlogic.mvvm_template.R


//Animate while loading an image
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_account)

    //For user-agent header parameter of https://jsonplaceholder.typicode.com/photos
    val placeholderImg = GlideUrl(
        url, LazyHeaders.Builder()
            .addHeader("User-Agent", "user-agent")
            .build()
    )
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(placeholderImg)
        .into(this)
}

//android:imageUrl can now be used to load images
@BindingAdapter("android:imgUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}
