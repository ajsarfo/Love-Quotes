package com.sarftec.lovequotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sarftec.lovequotes.data.database.dao.CategoryEntityDao
import com.sarftec.lovequotes.data.database.dao.QuoteEntityDao
import com.sarftec.lovequotes.data.database.entity.CategoryEntity
import com.sarftec.lovequotes.data.database.entity.QuoteEntity

@Database(entities = [QuoteEntity::class, CategoryEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun quoteDao() : QuoteEntityDao
    abstract fun categoryDao() : CategoryEntityDao
}