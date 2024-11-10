package com.example.signinsignup.data.repository

interface CacheLoginRepository {
    fun saveLoginData(id:String, password:String):Result<Boolean>
    fun getLoginData():Result<Pair<String?,String?>>
}