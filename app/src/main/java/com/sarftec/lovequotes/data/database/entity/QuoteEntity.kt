package com.sarftec.lovequotes.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.sarftec.lovequotes.data.database.QUOTE_TABLE

@Entity(
    tableName = QUOTE_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        )
    ]
)
class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true) val categoryId: Int,
    val message: String,
    var isFavorite: Boolean = false
)