package com.sarftec.lovequotes.presentation.binding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.lovequotes.BR
import com.sarftec.lovequotes.R
import com.sarftec.lovequotes.application.file.copy
import com.sarftec.lovequotes.application.file.share
import com.sarftec.lovequotes.application.file.toast
import com.sarftec.lovequotes.application.file.vibrate
import com.sarftec.lovequotes.application.model.Quote
import com.sarftec.lovequotes.application.tools.ImageHolder
import com.sarftec.lovequotes.presentation.adapter.ViewHolderListener
import com.sarftec.lovequotes.presentation.bindable

class ListItemBinding(
    val quote: Quote,
    private val listener: ViewHolderListener
) : BaseObservable() {

    @get:Bindable
    var favorite: ImageHolder by bindable(getDrawable(quote), BR.favorite)

    private fun getDrawable(quote: Quote) : ImageHolder {
      return ImageHolder.ImageDrawable(
          if(quote.isFavorite) R.drawable.favorite_checked
          else R.drawable.favorite_unchecked
      )
    }

    fun init() {
        favorite = getDrawable(quote)
    }

    fun onSpeak() {
        with(listener.getPack()) {
            context.vibrate()
            textSpeaker.play(quote.message)
        }
    }

    fun onShare() {
        with(listener.getPack()) {
            context.vibrate()
            context.share("\"${quote.message}\"", "share")
        }
    }

    fun onCopy() {
       with(listener.getPack()) {
           context.apply {
               vibrate()
               copy("\"${quote.message}\"", "share")
               toast("Copied to clipboard")
           }
       }
    }

    fun onFavorite() {
        quote.isFavorite = !quote.isFavorite
        favorite = getDrawable(quote)
        listener.apply {
            updateQuote(quote)
            getPack().context.vibrate()
            getPack().context.toast(
                if(quote.isFavorite) "Added to favorites"
            else "Removed from favorites"
            )
        }
    }
}