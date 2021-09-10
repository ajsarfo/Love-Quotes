package com.sarftec.lovequotes.application.model

class Quote(
    val id: Int,
    val categoryId: Int,
    val message: String,
    var isFavorite: Boolean = false
)