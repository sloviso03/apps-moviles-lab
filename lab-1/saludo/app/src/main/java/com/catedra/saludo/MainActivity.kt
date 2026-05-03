package com.catedra.saludo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.catedra.saludo.databinding.ActivityMainBinding
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }
    
    private fun setupListeners() {
        binding.nameBtn.setOnClickListener { onConfirmClicked() }
    }
    
    private fun onConfirmClicked() {
        val name = binding.name.text.toString().trim()
        
        if (name.isBlank()) {
            binding.name.error = "Ingresá tu nombre"
            return
        }
        navigateToGreeting(name)
    }
    
    private fun navigateToGreeting(name: String){
        val intent = Intent(this, ResultActivity::class.java).apply { 
            putExtra(Constants.KEY_NAME, name)
        }
        startActivity(intent)
    }
}