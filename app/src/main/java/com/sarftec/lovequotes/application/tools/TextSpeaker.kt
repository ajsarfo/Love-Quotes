package com.sarftec.lovequotes.application.tools

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import java.util.*

class TextSpeaker(context: Context) {


    private val textToSpeech by lazy {
        TextToSpeech(context, getListener())
    }

    private fun getListener() : TextToSpeech.OnInitListener {
        return TextToSpeech.OnInitListener {
            textToSpeech.language = Locale.UK
        }
    }

    fun play(text: String, pitch: Float = 0.5f) {
        textToSpeech.setPitch(pitch)
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
            return
        }
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "random")
    }

    fun stop() = textToSpeech.stop()

    fun shutdown() = textToSpeech.shutdown()
}