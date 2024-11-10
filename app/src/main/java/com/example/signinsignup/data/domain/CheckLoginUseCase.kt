package com.example.signinsignup.data.domain

import androidx.activity.OnBackPressedCallback
import com.example.signinsignup.data.model.User
import com.example.signinsignup.data.repository.FirebaseUserDataRepository
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(private val firebaseUserDataRepository: FirebaseUserDataRepository) {
    operator fun invoke(id:String, password:String, callback: (User?) -> Unit){
        firebaseUserDataRepository.Login(id,password){
            callback(it)
        }
    }
}