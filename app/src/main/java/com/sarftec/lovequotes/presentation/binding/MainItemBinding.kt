package com.sarftec.lovequotes.presentation.binding

import com.sarftec.lovequotes.application.model.Category

class MainItemBinding(
    val category: Category,
    private val onClick: (Category) -> Unit
) {

    fun onCategoryClick() = onClick(category)
}