package com.example.signinsignup.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CacheLoginRepositoryModule {
    @Binds
    abstract fun bindCacheLoginRepository(cacheLoginRepositoryImpl: CacheLoginRepositoryImpl):CacheLoginRepository
}