package com.erhansen.kotlincountries.util

import android.content.Context
import android.graphics.Paint
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.erhansen.kotlincountries.R
import kotlinx.android.synthetic.main.item_country.view.*

// Yapılacak olan extension : Oluşturulacak olan her imageview objesine glide için bir fonksiyon tanımlamak.

fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {

    //görseller inene kadar ne gösterileceği RequestOptions'ta yazılır.
    val requestOptions = RequestOptions
        .placeholderOf(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
            .load(url)
            .into(this)
    }

}

fun placeHolderProgressBar(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        //progress bar özellikleri
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadUrl") // Bu fonksiyon XML'de çalışabilir hale geldi.
fun downloadImage(imageView: ImageView, imageURL: String?) {
    imageView.downloadFromUrl(imageURL, placeHolderProgressBar(imageView.context))
}