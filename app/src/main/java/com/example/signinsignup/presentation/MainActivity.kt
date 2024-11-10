package com.example.signinsignup.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.signinsignup.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel:LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val isInit = intent.getBooleanExtra("isInit",true)

        initView()
        getUiState()
        if(isInit){
            viewModel.autoLogin()
        }else{
            binding.loginProgressbar.visibility=View.GONE
        }

    }

    fun initView() = with(binding){
        loginBtnLogin.setOnClickListener {
            val id = loginEtId.text.toString()
            val password = loginEtPassword.text.toString()
            val isAutoLogin = loginCbAutologin.isChecked
            if(id.isEmpty()||password.isEmpty()){
                Toast.makeText(this@MainActivity, "아이디와 비밀번호를 모두 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.CheckLogin(id,password,isAutoLogin)
        }

        loginBtnSignup.setOnClickListener {
            startActivity(Intent(this@MainActivity,SignUpActivity::class.java))
        }
    }

    fun getUiState() = with(binding){
        lifecycleScope.launch {
            viewModel.userDataUiState.collect{
                when(it){
                    is UiState.Failure -> {
                        loginProgressbar.visibility = View.GONE
                        if(it.e.first()=='L') Toast.makeText(this@MainActivity, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                    UiState.Init -> null
                    UiState.Loading -> loginProgressbar.visibility = View.VISIBLE
                    is UiState.Success -> {
                        Toast.makeText(this@MainActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity,LoginSuccessActivity::class.java)
                        intent.putExtra("id",it.data.id)
                        intent.putExtra("name",it.data.name)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}