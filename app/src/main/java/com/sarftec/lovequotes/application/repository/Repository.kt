package com.sarftec.lovequotes.application.repository

import com.sarftec.lovequotes.application.model.Category
import com.sarftec.lovequotes.application.model.Quote

interface Repository {
    suspend fun getCategory(categoryId: Int) : Category?
    suspend fun fetchCategories() : List<Category>
    suspend fun fetchQuotes(categoryId: Int) : List<Quote>
    suspend fun fetchFavorites() : List<Quote>
    suspend fun updateQuote(quote: Quote)
}