package com.sarftec.lovequotes.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sarftec.lovequotes.R

abstract class BaseActivity : AppCompatActivity() {

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