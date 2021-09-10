package com.sarftec.lovequotes.presentation.listener

import com.sarftec.lovequotes.application.model.Category

interface MainActivityListener : ActivityListener {
    fun navigate(category: Category)
}