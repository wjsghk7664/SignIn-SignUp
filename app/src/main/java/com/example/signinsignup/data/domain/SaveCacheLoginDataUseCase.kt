package com.example.signinsignup.data.domain

import com.example.signinsignup.data.repository.CacheLoginRepository
import javax.inject.Inject

class SaveCacheLoginDataUseCase @Inject constructor(private val cacheLoginRepository: CacheLoginRepository) {
    operator fun invoke(id:String, password:String):Boolean?{
        return cacheLoginRepository.saveLoginData(id,password).getOrNull()
    }
}