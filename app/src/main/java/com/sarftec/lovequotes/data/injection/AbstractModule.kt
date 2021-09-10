package com.sarftec.lovequotes.data.injection

import com.sarftec.lovequotes.application.repository.Repository
import com.sarftec.lovequotes.data.repository.DiskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {

    @Singleton
    @Binds
    abstract fun repository(memoryRepository: DiskRepositoryImpl) : Repository
}