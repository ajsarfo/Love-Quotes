package com.sarftec.lovequotes.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.sarftec.lovequotes.R
import com.sarftec.lovequotes.application.manager.AdCountManager
import com.sarftec.lovequotes.application.manager.InterstitialManager
import com.sarftec.lovequotes.application.manager.NetworkManager
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var networkManager: NetworkManager

    protected val adRequestBuilder: AdRequest by lazy {
        AdRequest.Builder().build()
    }

    protected var interstitialManager: InterstitialManager? = null

    protected open fun canShowInterstitial() : Boolean = true

    protected open fun createAdCounterManager() : AdCountManager {
        return AdCountManager(listOf(1, 4, 3))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Load interstitial if required by extending activity
        if(!canShowInterstitial()) return
        interstitialManager = InterstitialManager(
            this,
            getString(R.string.admob_interstitial_id),
            networkManager,
            createAdCounterManager(),
            adRequestBuilder
        )
        interstitialManager?.load()
    }

    fun <T> navigateTo(
        klass: Class<T>,
        finish: Boolean = false,
        slideIn: Int = R.anim.slide_in_right,
        slideOut: Int = R.anim.slide_out_left,
        bundle: Bundle? = null
    ) {
        val intent = Intent(this, klass).also {
            it.putExtra(ACTIVITY_BUNDLE, bundle)
        }
        startActivity(intent)
        if (finish) finish()
        overridePendingTransition(slideIn, slideOut)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    companion object {
        const val ACTIVITY_BUNDLE = "activity_bundle"
        const val CATEGORY_ID = "category_id"
    }
}