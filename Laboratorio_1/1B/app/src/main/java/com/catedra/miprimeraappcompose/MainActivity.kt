package com.catedra.miprimeraappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.catedra.miprimeraappcompose.Screen
import com.catedra.miprimeraappcompose.ui.theme.MiPrimeraAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiPrimeraAppComposeTheme {
                
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Calculator) }
                var amount by remember { mutableStateOf("") }
                var selectedPercentage by remember { mutableStateOf<Int?>(null) }

                when (val screen = currentScreen) {
                    is Screen.Calculator -> CalculatorScreen(
                        amount = amount,
                        onAmountChange = { amount = it },
                        selectedPercentage = selectedPercentage,
                        onPercentageChange = { selectedPercentage = it },
                        onCalculate = {
                            currentScreen = Screen.Result(
                                amount = amount.toDouble(),
                                percentage = selectedPercentage!!
                            )
                        }
                    )
                    is Screen.Result -> ResultScreen(
                        amount = screen.amount,
                        percentage = screen.percentage,
                        onBack = { currentScreen = Screen.Calculator }
                    )
                }
            }
        }
    }
}