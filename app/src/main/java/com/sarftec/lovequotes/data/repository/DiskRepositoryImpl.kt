
package com.sarftec.lovequotes.data.repository

import com.sarftec.lovequotes.application.model.Category
import com.sarftec.lovequotes.application.model.Quote
import com.sarftec.lovequotes.application.repository.Repository
import com.sarftec.lovequotes.data.database.Database
import com.sarftec.lovequotes.data.database.entity.QuoteEntity
import javax.inject.Inject

class DiskRepositoryImpl @Inject constructor(
    private val database: Database
): Repository {

    override suspend fun getCategory(categoryId: Int): Category {
       return database.categoryDao().category(categoryId).let {
            Category(it.id, it.category)
        }
    }

    override suspend fun fetchCategories(): List<Category> {
       return database.categoryDao().categories().map {
            Category(it.id, it.category)
        }
    }

    override suspend fun fetchQuotes(categoryId: Int): List<Quote> {
       return database.quoteDao().quotes(categoryId).map {
            Quote(it.id, it.categoryId, it.message, it.isFavorite)
        }
    }

    override suspend fun fetchFavorites(): List<Quote> {
        return database.quoteDao().favorites().map {
            Quote(it.id, it.categoryId, it.message, it.isFavorite)
        }
    }

    override suspend fun updateQuote(quote: Quote) {
        database.quoteDao().update(
            quote.run {
                QuoteEntity(id, categoryId, message, isFavorite)
            }
        )
    }
}