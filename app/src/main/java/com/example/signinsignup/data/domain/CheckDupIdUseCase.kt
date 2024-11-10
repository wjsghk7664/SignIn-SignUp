package com.example.signinsignup.data.domain

import com.example.signinsignup.data.repository.FirebaseUserDataRepository
import com.example.signinsignup.data.repository.FirebaseUserDataRepositoryImpl
import javax.inject.Inject

class CheckDupIdUseCase @Inject constructor(private val firebaseUserDataRepository: FirebaseUserDataRepository){
    operator fun invoke(id:String, callback:(Boolean?)->Unit){
        if(id.isEmpty()){
            callback(false)
            return
        }
        firebaseUserDataRepository.CheckDupId(id){ isEnable, type ->
            if(isEnable){
                callback(true)
            }else{
                if(type==0){
                    callback(false)
                }else{
                    callback(null)
                }
            }
        }
    }
}