package com.sarftec.lovequotes.data.injection

import android.content.Context
import androidx.room.Room
import com.sarftec.lovequotes.data.database.DATABASE_NAME
import com.sarftec.lovequotes.data.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class StaticModule {

    @Provides
    fun quoteDatabase(@ApplicationContext context: Context) : Database {
        return Room.databaseBuilder(context, Database::class.java, DATABASE_NAME)
            .build()
    }
}