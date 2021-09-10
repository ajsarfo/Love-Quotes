package com.sarftec.lovequotes.presentation

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.sarftec.lovequotes.application.tools.ImageHolder
import kotlin.reflect.KProperty

class Bindable<T : Any>(private var value: T, private val tag: Int) {
    operator fun <U : BaseObservable> getValue(ref: U, property: KProperty<*>): T = value
    operator fun <U : BaseObservable> setValue(ref: U, property: KProperty<*>, newValue: T) {
        value = newValue
        ref.notifyPropertyChanged(tag)
    }
}


fun <T : Any> bindable(value: T, tag: Int): Bindable<T> = Bindable(value, tag)

@BindingAdapter("image")
fun changeImage(imageView: ImageView, imageHolder: ImageHolder?) {
    imageHolder?.let {
        when (it) {
            is ImageHolder.Empty -> imageView.setImageBitmap(null)
            is ImageHolder.ImageBitmap -> imageView.setImageBitmap(it.image)
            is ImageHolder.ImageDrawable -> imageView.setImageResource(it.icon)
            is ImageHolder.ImageColor -> imageView.setBackgroundColor(it.color)
        }
    }
}

@BindingAdapter("color")
fun changeBackgroundColor(cardView: MaterialCardView, color: Int) {
    cardView.setCardBackgroundColor(color)
}