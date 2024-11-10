package com.example.signinsignup.data.repository

import com.example.signinsignup.data.model.User

interface FirebaseUserDataRepository {
    fun CheckDupId(id:String, callback:(Boolean,Int) ->Unit)
    fun AddUserData(user: User, callback: (Boolean) -> Unit)
    fun Login(id:String, password:String, callback: (User?) -> Unit)
}