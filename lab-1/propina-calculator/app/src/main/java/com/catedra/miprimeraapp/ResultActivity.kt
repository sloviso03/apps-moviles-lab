package com.catedra.miprimeraapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.catedra.miprimeraapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val amount = intent.getDoubleExtra(Constants.KEY_AMOUNT, 0.0)
        val percentage = intent.getIntExtra(Constants.KEY_PERCENTAGE, 0)

        val tip = amount * percentage / 100
        val total = amount + tip

        binding.textBillAmount.text = "$ %.2f".format(amount)
        binding.textTipAmount.text  = "$ %.2f".format(tip)
        binding.textTotalAmount.text = "$ %.2f".format(total)

        binding.buttonBack.setOnClickListener { finish() }
    }
}