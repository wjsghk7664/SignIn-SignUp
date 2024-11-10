package com.example.signinsignup.data.domain

import com.example.signinsignup.data.repository.CacheLoginRepository
import javax.inject.Inject

class GetCacheLoginDataUseCase @Inject constructor(private val cacheLoginRepository: CacheLoginRepository) {
    operator fun invoke():Pair<String?,String?>?{
        return cacheLoginRepository.getLoginData().getOrNull()
    }
}