package com.catedra.miprimeraappcompose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorScreen(amount: String, onAmountChange: (String) -> Unit, selectedPercentage: Int?, onPercentageChange: (Int) -> Unit, onCalculate: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
            text = "Calculadora de propina",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 32.dp, bottom = 4.dp)
        )

        Text(
            text = "Calcula tus propinas rápidamente",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Ingresar monto total  (sólo número)",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = amount,
            onValueChange = onAmountChange,
            placeholder = { Text("42000") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Text(
            text = "PORCENTAJE DE PROPINA",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(modifier = Modifier.selectableGroup()) {
            listOf(10, 15, 20).forEach { percentage ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedPercentage == percentage,
                        onClick = { onPercentageChange(percentage) }
                    )
                    Text(
                        text = "$percentage%",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp, top = 12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onCalculate,
            enabled = amount.toDoubleOrNull() != null && selectedPercentage != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CALCULAR")
        }
    }
}





@Composable
fun ResultScreen(amount: Double, percentage: Int, onBack: () -> Unit, modifier: Modifier = Modifier) {
    BackHandler(onBack = onBack)

    val tip = amount * percentage / 100
    val total = amount + tip

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
            text = "Resumen",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 32.dp, bottom = 48.dp)
        )

        Text("MONTO FACTURA", style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("$ %.2f".format(amount), style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp))

        Text("PROPINA", style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("$ %.2f".format(tip), style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp))

        HorizontalDivider(modifier = Modifier.padding(bottom = 24.dp))

        Text("TOTAL", style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("$ %.2f".format(total), style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 48.dp))

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CALCULAR OTRA VEZ")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen(
        amount = "42000",
        onAmountChange = {},
        selectedPercentage = 10,
        onPercentageChange = {},
        onCalculate = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        amount = 42000.0,
        percentage = 10,
        onBack = {}
    )
}
