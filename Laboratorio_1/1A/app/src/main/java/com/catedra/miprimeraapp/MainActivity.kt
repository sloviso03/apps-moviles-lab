package com.catedra.miprimeraapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.catedra.miprimeraapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonCalculate.setOnClickListener { onCalculateClicked() }
    }

    private fun onCalculateClicked() {
        val amount = binding.editTextAmount.text.toString().toDoubleOrNull()
        val percentage = getSelectedPercentage()

        if (amount == null) {
            binding.editTextAmount.error = "Enter a valid amount"
            return
        }
        if (percentage == null) {
            binding.radioGroupPercentage.requestFocus()
            return
        }

        navigateToResult(amount, percentage)
    }

    private fun navigateToResult(amount: Double, percentage: Int) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(Constants.KEY_AMOUNT, amount)
            putExtra(Constants.KEY_PERCENTAGE, percentage)
        }
        startActivity(intent)
    }

    private fun getSelectedPercentage(): Int? = when (binding.radioGroupPercentage.checkedRadioButtonId) {
        R.id.radio10 -> 10
        R.id.radio15 -> 15
        R.id.radio20 -> 20
        else -> null
    }
}