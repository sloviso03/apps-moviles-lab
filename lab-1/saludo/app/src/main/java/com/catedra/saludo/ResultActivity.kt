package com.catedra.saludo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.catedra.saludo.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityResultBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        displayGreeting()
    }
    private fun setupListeners() {
        binding.buttonBack.setOnClickListener { finish() }
    }

    private fun displayGreeting() {
        val name = intent.getStringExtra(Constants.KEY_NAME) ?: "desconocido"
        binding.textGreeting.text = "Hola, $name!"
    }
}