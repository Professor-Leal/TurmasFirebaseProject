package com.rafaelleal.android.turmasfirebaseproject.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.rafaelleal.android.turmasfirebaseproject.databinding.ActivityMainBinding
import com.rafaelleal.android.turmasfirebaseproject.login.ui.LoginActivity

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel>()

    // Usar a vinculação de visualizações em atividades
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#activities
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }



    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnSair.setOnClickListener {
            viewModel.logout()
            startLoginActivity()
        }
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setupViews() {
        setTitle("Principal")
        binding.tvBemVindo.text = "Seja bem vindo ${viewModel.getCurrentUserEmail()}!"
    }






}