package com.sarftec.lovequotes.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class BaseListViewModel : ViewModel() {

    protected var baseBundle: Bundle? = null

    abstract fun getToolbarTitle(): StateFlow<String?>

    abstract fun fetch()

    fun setBundle(bundle: Bundle?) {
        if (this.baseBundle != null) return
        this.baseBundle = bundle
    }
}