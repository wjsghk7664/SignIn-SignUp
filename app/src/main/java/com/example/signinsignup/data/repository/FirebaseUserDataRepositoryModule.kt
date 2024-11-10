package com.example.signinsignup.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FirebaseUserDataRepositoryModule {

    @Binds
    abstract fun bindFirebaseUserDataRepository(firebaseUserDataRepositoryImpl: FirebaseUserDataRepositoryImpl):FirebaseUserDataRepository
}