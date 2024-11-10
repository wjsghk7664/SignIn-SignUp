package com.example.signinsignup.presentation

import androidx.lifecycle.ViewModel
import com.example.signinsignup.data.domain.CheckLoginUseCase
import com.example.signinsignup.data.domain.GetCacheLoginDataUseCase
import com.example.signinsignup.data.domain.SaveCacheLoginDataUseCase
import com.example.signinsignup.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveCacheLoginDataUseCase: SaveCacheLoginDataUseCase,
    private val getCacheLoginDataUseCase: GetCacheLoginDataUseCase,
    private val checkLoginUseCase: CheckLoginUseCase
):ViewModel() {

    private val _userDataUiState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val userDataUiState = _userDataUiState.asStateFlow()

    fun CheckLogin(id: String, password: String, isCache: Boolean) {
        _userDataUiState.value = UiState.Loading
        checkLoginUseCase(id,password){ user ->
            if(user!=null){
                var result = true
                if(isCache){
                    result=saveCacheLoginDataUseCase(id,password)?:false
                }
                if(result){
                    _userDataUiState.value = UiState.Success(user)
                }else{
                    _userDataUiState.value = UiState.Failure("L:SaveLoginDataFail")
                }
            }
            else{
                _userDataUiState.value = UiState.Failure("L:UserDataNotMatch")
            }
        }
    }

    fun autoLogin(){
        val result = getCacheLoginDataUseCase()
        if(result==null){
            _userDataUiState.value = UiState.Failure("A: NoLoginData")
        }else{
            val id = result.first
            val password = result.second
            if(id!=null&&password!=null){
                CheckLogin(id,password,true)
            }else{
                _userDataUiState.value = UiState.Failure("A:LoadDataFailure")
            }
        }
    }
}