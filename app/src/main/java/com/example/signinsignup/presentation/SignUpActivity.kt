package com.example.signinsignup.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.signinsignup.R
import com.example.signinsignup.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private val viewmodel:SignUpViewModel by viewModels()

    private var idFlag = false
    private var passwordFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        getUiState()
    }

    fun initView() = with(binding){
        signupEtId.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewmodel.checkId(s.toString())
            }

        })

        signupEtPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewmodel.checkPassword(s.toString())
            }

        })

        signupBtnSignup.setOnClickListener {
            if(!idFlag||!passwordFlag) {
                Toast.makeText(this@SignUpActivity, "올바르게 빈 칸을 채워주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val id = signupEtId.text.toString()
            val password = signupEtPassword.text.toString()
            val name = signupEtName.text.toString()
            viewmodel.signUp(id, password, name)
        }
    }

    fun getUiState() = with(binding){
        lifecycleScope.launch {
            viewmodel.SignUpUiState.collect{
                when(it){
                    is UiState.Failure -> Toast.makeText(this@SignUpActivity, "회원가입에 실패하였습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    UiState.Init -> null
                    UiState.Loading -> null
                    is UiState.Success -> {
                        val type = it.data.first
                        val result = it.data.second
                        when(type){
                            1 -> {
                                idFlag = result?:false
                                signupTvIsdup.setText(if(result==null) "오류발생. 다시 입력해 주세요." else if(result) "사용 가능한 아이디입니다." else "사용 불가능한 아이디입니다.")
                            }
                            2 -> {
                                passwordFlag = result?:false
                                signupTvIscorrect.setText(if(result!!) "사용 가능한 비밀번호 입니다." else "영문과 숫자, 특수문자를 하나 이상 넣어서 8자리 이상으로 입력하여 주세요.")
                            }
                            else -> {
                                Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}