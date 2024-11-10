package com.example.signinsignup.presentation

import androidx.lifecycle.ViewModel
import com.example.signinsignup.data.domain.CheckDupIdUseCase
import com.example.signinsignup.data.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkDupIdUseCase: CheckDupIdUseCase,
    private val signUpUseCase: SignUpUseCase
    ): ViewModel(){

    private val _SignUpUiState = MutableStateFlow<UiState<Pair<Int,Boolean?>>>(UiState.Init) //1. 아이디, 2. 비밀번호, 3. 회원가입 성공 여부
    val SignUpUiState = _SignUpUiState.asStateFlow()

    fun checkId(id:String){
        checkDupIdUseCase(id){
            _SignUpUiState.value = UiState.Success(Pair(1,it))
        }
    }

    fun checkPassword(password:String){
        val regex= Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\W_]).{8,}$")
        if(regex.matches(password)){
            _SignUpUiState.value = UiState.Success(Pair(2,true))
        }else{
            _SignUpUiState.value = UiState.Success(Pair(2,false))
        }
    }

    fun signUp(id:String, password: String, name: String){
        signUpUseCase(id,password,name){
            if(it){
                _SignUpUiState.value = UiState.Success(Pair(3,true))
            }else{
                _SignUpUiState.value = UiState.Failure("error")
            }
        }
    }

}