package com.catedra.peliculas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.catedra.peliculas.databinding.ActivityMainBinding
import com.catedra.peliculas.ui.peliculas.PeliculasFragment

/**
 * Activity unica que aloja los Fragments de la app.
 *
 * No modificar este archivo.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Solo cargar el Fragment inicial cuando la Activity se crea por primera vez.
        // En cambios de configuracion (rotacion), el FragmentManager restaura el Fragment
        // automaticamente y no queremos crear uno nuevo encima.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, PeliculasFragment())
                .commit()
        }
    }
}
