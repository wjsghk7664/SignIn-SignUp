package com.example.signinsignup.data.repository

import com.example.signinsignup.data.source.CacheLoginDataSource
import javax.inject.Inject

class CacheLoginRepositoryImpl @Inject constructor(private val cacheLoginDataSource: CacheLoginDataSource):CacheLoginRepository {
    override fun saveLoginData(id: String, password: String): Result<Boolean> {
        return runCatching {
            cacheLoginDataSource.saveLoginData(id,password)
        }
    }

    override fun getLoginData(): Result<Pair<String?, String?>> {
        return runCatching {
            cacheLoginDataSource.getLoginData()
        }
    }
}