package com.catedra.miprimeraapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        // Solo cargamos el Fragment inicial la primera vez
        // Si savedInstanceState != null, el sistema ya lo restauró
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CalculatorFragment())
                .commit()
        }
    }
}