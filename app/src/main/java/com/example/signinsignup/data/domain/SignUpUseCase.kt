package com.example.signinsignup.data.domain

import androidx.activity.OnBackPressedCallback
import com.example.signinsignup.data.model.User
import com.example.signinsignup.data.repository.FirebaseUserDataRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val firebaseUserDataRepository: FirebaseUserDataRepository) {
    operator fun invoke(id:String, password:String, name:String, callback: (Boolean)->Unit){
        firebaseUserDataRepository.AddUserData(User(id,password,name)){
            callback(it)
        }
    }
}