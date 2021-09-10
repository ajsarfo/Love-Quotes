package com.sarftec.lovequotes.presentation.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.sarftec.lovequotes.R
import com.sarftec.lovequotes.data.DataSetup
import com.sarftec.lovequotes.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var dataSetup: DataSetup

    private val binding by lazy {
        ActivitySplashBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setImages()
        lifecycleScope.launchWhenCreated {
            val timeDelay = measureTimeMillis {
                if(!dataSetup.isCreated()) {
                    binding.splashLoadingCard.visibility = View.VISIBLE
                    dataSetup.create()
                }
            }
            val difference = TimeUnit.SECONDS.toMillis(3) - timeDelay
            if(difference > 0) delay(difference)
            navigateTo(
                MainActivity::class.java,
                true,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }
    }

    private fun setImages() {
        //Set splash background
        assets.open("images/background.jpg").use {
            binding.backgroundImage.setImageBitmap(
                BitmapFactory.decodeStream(it)
            )
        }
        //Set splash icon
        assets.open("images/icon.png").use {
            binding.splashIcon.setImageBitmap(
                BitmapFactory.decodeStream(it)
            )
        }
    }
}