package com.sarftec.lovequotes.data.database.dao

import androidx.room.*
import com.sarftec.lovequotes.data.database.QUOTE_TABLE
import com.sarftec.lovequotes.data.database.entity.QuoteEntity

@Dao
interface QuoteEntityDao {

    @Query("select * from $QUOTE_TABLE")
    suspend fun quotes() : List<QuoteEntity>

    @Query("select * from $QUOTE_TABLE where categoryId = :categoryId")
    suspend fun quotes(categoryId: Int) : List<QuoteEntity>

    @Query("select * from $QUOTE_TABLE where isFavorite = 1")
    suspend fun favorites() : List<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quotes: List<QuoteEntity>)

    @Update
    suspend fun update(quote: QuoteEntity)
}