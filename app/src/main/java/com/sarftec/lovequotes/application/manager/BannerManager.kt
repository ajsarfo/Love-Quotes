package com.sarftec.lovequotes.application.manager

import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.sarftec.lovequotes.application.file.toast

class BannerManager(
    private val activity: AppCompatActivity,
    private val adRequest: AdRequest = AdRequest.Builder().build()
) {

    /*
     fun loadBanner(admobView: AdView) {
         admobView.loadAd(adRequest)
         admobView.adSize = computeAdaptiveAdSize(admobView)
     }
     */

    fun attachBannerAd(bannerId: String, parent: FrameLayout) {
        val adView = AdView(activity)
        parent.removeAllViews()
        parent.addView(adView, 0)
        adView.adSize = computeAdaptiveAdSize(adView)
        adView.adUnitId = bannerId
        adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
               activity.toast("Banner failed to load! => ${p0.message}", Toast.LENGTH_LONG)
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                activity.toast("Banner loaded!", Toast.LENGTH_LONG)
            }
        }
        adView.loadAd(adRequest)
    }

    private fun computeAdaptiveAdSize(admobView: AdView) : AdSize {
        val dimension = Point()
        val density: Float

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.windowManager.currentWindowMetrics.bounds.let {
                dimension.x = it.width()
                dimension.y = it.height()
            }
            density = activity.resources.configuration.densityDpi.toFloat()
        }
        else {
            val display = activity.windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
            dimension.x = outMetrics.widthPixels
            dimension.y = outMetrics.heightPixels
            density = outMetrics.density
        }

        var adWidthPixels = admobView.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = dimension.x.toFloat()
        }

        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
    }
}