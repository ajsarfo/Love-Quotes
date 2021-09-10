package com.sarftec.lovequotes.data

import android.content.Context
import com.sarftec.lovequotes.application.file.APP_CREATED
import com.sarftec.lovequotes.application.file.editSettings
import com.sarftec.lovequotes.application.file.readSettings
import com.sarftec.lovequotes.data.database.Database
import com.sarftec.lovequotes.data.database.entity.CategoryEntity
import com.sarftec.lovequotes.data.database.entity.QuoteEntity
import com.sarftec.lovequotes.data.json.JsonQuotes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*
import javax.inject.Inject

class DataSetup @Inject constructor(
    private val database: Database,
    @ApplicationContext private val context: Context
) {

    private val quoteFolder = "quotes"
    private val categories = context.assets.list(quoteFolder)!!
        .asList()
        .sortedBy { it.lowercase(Locale.ENGLISH) }

    suspend fun isCreated(): Boolean {
        return context.readSettings(APP_CREATED, false).first()
    }

    suspend fun create() {
        database.categoryDao().insert(
            categories.map { it.substringBeforeLast(".json") }
                .mapIndexed { index, category -> CategoryEntity(index, category) }
        )
        categories.mapIndexed { index, fileName -> insertQuotes(index, fileName) }
        context.editSettings(APP_CREATED, true)
    }

    private suspend fun insertQuotes(categoryId: Int, fileName: String) {
        context.assets.open("$quoteFolder/$fileName").use { inputStream ->
            val jsonQuotes: JsonQuotes = Json.decodeFromString(
                inputStream.bufferedReader().readText()
            )
           database.quoteDao().insert(
               jsonQuotes.quotes.map {
                   QuoteEntity(categoryId = categoryId, message = it)
               }
           )
        }
    }
}