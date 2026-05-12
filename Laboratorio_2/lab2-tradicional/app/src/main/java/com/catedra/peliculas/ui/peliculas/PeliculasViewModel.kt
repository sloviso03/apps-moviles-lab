package com.catedra.peliculas.ui.peliculas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catedra.peliculas.data.model.Pelicula
import com.catedra.peliculas.data.repository.PeliculasRepositorio
import kotlinx.coroutines.launch

class PeliculasViewModel (private val repositorio: PeliculasRepositorio = PeliculasRepositorio()) : ViewModel() {
    
    /* Antes del Live Data
     var peliculas: List<Pelicula> = emptyList()
        private set;
    var peliculasFiltradas: List<Pelicula> = emptyList()
        private set;
    
    var cargando: Boolean = true
        private set;
    
    var error: String? = null
        private set;
                
     */
    private val _peliculas = MutableLiveData<List<Pelicula>>(emptyList())
    val peliculas: LiveData<List<Pelicula>> = _peliculas;

    private val _peliculasFiltradas = MutableLiveData<List<Pelicula>>(emptyList())
    val peliculasFiltradas: LiveData<List<Pelicula>> = _peliculasFiltradas;
    
    private val _cargando = MutableLiveData<Boolean>(true)
    val cargando: LiveData<Boolean> = _cargando
    
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error;
    
    
    
    init { cargarPeliculas() }
    
    
    fun cargarPeliculas() {
        // TODO Etapa 2a: implementa esta funcion
        // Debe:
        // 1. Marcar cargando = true y error = null
        // 2. Llamar a repositorio.obtenerPeliculas() en una coroutine
        // 3. Si exitoso: guardar películas, actualizar peliculasFiltradas
        // con la lista completa y marcar cargando = false
        // 4. Si falla: guardar el mensaje de error y marcar cargando = false

        _cargando.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val resultado = repositorio.obtenerPeliculas()
                _peliculas.value = resultado
                _peliculasFiltradas.value = resultado
                _cargando.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _cargando.value = false
            }
        }
    }
    
    
    fun actualizarBusqueda(consulta: String) {
        // TODO Etapa 2b: implementa esta funcion
        // Debe filtrar películas por titulo segun la consulta
        // Si la consulta esta vacía, peliculasFiltradas = películas completa
        // Si no, filtrar por titulo ignorando mayúsculas
        _peliculasFiltradas.value = if (consulta.isBlank()) {
            _peliculas.value ?: emptyList()
        } else {
            (_peliculas.value ?: emptyList()).filter { it.titulo.contains(consulta, ignoreCase = true) }
        }
    }
    
    
}