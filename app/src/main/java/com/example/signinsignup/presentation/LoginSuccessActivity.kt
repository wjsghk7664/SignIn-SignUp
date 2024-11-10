package com.example.signinsignup.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.signinsignup.R
import com.example.signinsignup.databinding.ActivityLoginSuccessBinding

class LoginSuccessActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginSuccessBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = "아이디: " + intent.getStringExtra("id")
        val name = "이름: " + intent.getStringExtra("name")

        binding.loginSuccessTvId.setText(id)
        binding.loginSuccessTvName.setText(name)
        binding.loginSuccessBtnLogout.setOnClickListener {
            val intent = Intent(this@LoginSuccessActivity, MainActivity::class.java)
            intent.putExtra("isInit",false)
            startActivity(intent)
            finish()
        }
    }
}