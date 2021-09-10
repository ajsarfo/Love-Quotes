package com.sarftec.lovequotes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sarftec.lovequotes.data.database.CATEGORY_TABLE


@Entity(tableName = CATEGORY_TABLE)
class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val category: String
)