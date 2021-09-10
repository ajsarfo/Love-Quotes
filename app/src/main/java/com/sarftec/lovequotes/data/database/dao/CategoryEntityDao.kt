package com.sarftec.lovequotes.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.lovequotes.data.database.CATEGORY_TABLE
import com.sarftec.lovequotes.data.database.entity.CategoryEntity

@Dao
interface CategoryEntityDao {

    @Query("select * from $CATEGORY_TABLE")
    suspend fun categories() : List<CategoryEntity>

    @Query("select * from $CATEGORY_TABLE where id = :categoryId")
    suspend fun category(categoryId: Int) : CategoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<CategoryEntity>)
}