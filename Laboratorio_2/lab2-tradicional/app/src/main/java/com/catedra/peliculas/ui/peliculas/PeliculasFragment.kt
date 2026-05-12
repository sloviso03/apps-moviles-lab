package com.catedra.peliculas.ui.peliculas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.catedra.peliculas.R
import com.catedra.peliculas.data.model.Pelicula
import com.catedra.peliculas.data.repository.PeliculasRepositorio
import com.catedra.peliculas.databinding.FragmentPeliculasBinding
import com.catedra.peliculas.ui.detalle.DetalleFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Pantalla del listado de peliculas.
 *
 * Sin ViewModel, este Fragment es responsable de TODO: guardar el estado,
 * hablar con el repositorio, gestionar coroutines y actualizar la UI.
 * En el Lab 2A vas a ver como esa logica se mueve a un ViewModel.
 */
class PeliculasFragment : Fragment() {

    private var _binding: FragmentPeliculasBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PeliculasViewModel
    private lateinit var adapter: PeliculaAdapter
    
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeliculasBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO 3a: inicializa el ViewModel
        // Usa ViewModelProvider(this).get(PeliculasViewModel::class.java)
        // o la forma delegada: private val viewModel by viewModels()
        viewModel = ViewModelProvider(this).get(PeliculasViewModel::class.java)
        
        // TODO 3b: configura el adapter y el RecyclerView
        // - Crea una instancia de PeliculaAdapter con un lambda onItemClick
        // que llame a navegarADetalle(película)
        // - Asigna el adapter al binding.recyclerView
        adapter = PeliculaAdapter { pelicula -> navegarADetalle(pelicula) }
        binding.recyclerView.adapter = adapter;
        
        
        // TODO 3c: carga los datos y actualiza la UI
        // - Llama a viewModel.cargarPeliculas()
        // - Segun el estado del ViewModel (cargando, error, datos):
        // mostra u oculta binding.progressBar y binding.textError
        // y actualiza el adapter con viewModel.peliculasFiltradas
        /* ANTES
        lifecycleScope.launch {
            actualizarUI()
            while (viewModel.cargando) {
                delay(50)
            }
            actualizarUI()             
         */
        viewModel.cargando.observe(viewLifecycleOwner) { cargando ->
            binding.progressBar.isVisible = cargando
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.textError.isVisible = error != null
            binding.textError.text = error.orEmpty()
        }
        viewModel.peliculasFiltradas.observe(viewLifecycleOwner) { peliculas ->
            adapter.submitList(peliculas)
        }
        
        // TODO 3d: conecta el campo de búsqueda
        // - Agrega un TextWatcher a binding.editTextBusqueda
        // - En onTextChanged: llama a viewModel.actualizarBusqueda(texto)
        // y actualiza el adapter con viewModel.peliculasFiltradas
        /* ANTES
        binding.editTextBusqueda.addTextChangedListener { text ->
            viewModel.actualizarBusqueda(text?.toString() ?: "")
            actualizarUI()
        }        
         */
        binding.editTextBusqueda.addTextChangedListener { text ->
            viewModel.actualizarBusqueda(text?.toString() ?: "")
        }
    }

    /* Ya no hace falta
    private fun actualizarUI() {
        binding.progressBar.isVisible = viewModel.cargando
        binding.textError.isVisible = viewModel.error != null
        binding.textError.text = viewModel.error.orEmpty()
        adapter.submitList(viewModel.peliculasFiltradas)
    }    
     */
    
    private fun navegarADetalle(pelicula: Pelicula) {
        // TODO 3e: navega al DetalleFragment
        // - Crea una instancia de DetalleFragment con DetalleFragment.newInstance(pelicula.id)
        // - Usa parentFragmentManager.beginTransaction() para reemplazar
        // el fragment actual por el DetalleFragment
        // - Agrega la transaccion al back stack con addToBackStack(null)
        // - Committea la transaccion
        val fragment = DetalleFragment.newInstance(pelicula.id)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()

    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null 
    }

    
}
