package com.catedra.peliculas.ui.peliculas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.catedra.peliculas.R
import com.catedra.peliculas.data.model.Pelicula
import com.catedra.peliculas.data.repository.PeliculasRepositorio
import com.catedra.peliculas.databinding.FragmentPeliculasBinding
import com.catedra.peliculas.ui.detalle.DetalleFragment
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

    private lateinit var adapter: PeliculaAdapter

    // Repositorio - el mismo que vas a usar en el Lab 2A
    private val repositorio = PeliculasRepositorio()

    // TODO Etapa 2a: declara las propiedades de estado del Fragment
    //
    // Necesitas cuatro propiedades, todas con `private set`
    // (publicas para lectura, privadas para escritura desde fuera de la clase):
    //
    //  - peliculas: List<Pelicula>, vacia por defecto
    //  - peliculasFiltradas: List<Pelicula>, vacia por defecto
    //  - cargando: Boolean, true por defecto
    //  - error: String nullable, null por defecto
    //
    // Es exactamente el mismo conjunto de propiedades que vas a ver en el
    // ViewModel del Lab 2A. La diferencia es solo donde viven.
    
    var peliculas: List<Pelicula> = emptyList() 
        private set;
    
    var peliculasFiltradas: List<Pelicula> = emptyList()
        private set;
    
    var cargando: Boolean = true
        private set;
    
    var error: String? = null
        private set;
    

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

        // TODO Etapa 2b: configura el adapter y el RecyclerView
        //
        //  - Crea PeliculaAdapter con un lambda onItemClick que llame a
        //    navegarADetalle(pelicula). Esa funcion la implementas en la Etapa 3.
        //  - Asigna el adapter al binding.recyclerView (binding.recyclerView.adapter = adapter)
        //
        // El LayoutManager ya esta configurado en el XML, no necesitas asignarlo.
        
        adapter = PeliculaAdapter { 
            pelicula -> navegarADetalle(pelicula);
        };
        
        binding.recyclerView.adapter = adapter;

        // TODO Etapa 2c: dispara la carga inicial
        //
        //  - Llama a cargarPeliculas()
        cargarPeliculas();

        // TODO Etapa 3a: conecta el campo de busqueda
        //
        // Lo vas a implementar en la Etapa 3.
        binding.editTextBusqueda.addTextChangedListener { text ->
            actualizarBusqueda(text?.toString() ?: "");
        }
    }

    private fun cargarPeliculas() {
        // TODO Etapa 2d: implementa esta funcion
        //
        // Pasos:
        //  1. Marcar cargando = true y error = null
        //  2. Llamar a actualizarUI() para reflejar el estado de carga en pantalla
        //  3. Lanzar una coroutine con lifecycleScope.launch { ... }
        //     (notar que NO es viewModelScope porque no hay ViewModel)
        //  4. Dentro de la coroutine, rodear con try { } catch (e: Exception) { }:
        //       - try: llamar a repositorio.obtenerPeliculas(), guardar el
        //         resultado en peliculas, copiar a peliculasFiltradas, marcar
        //         cargando = false, llamar a actualizarUI()
        //       - catch: guardar e.message en error, marcar cargando = false,
        //         llamar a actualizarUI()
        
        cargando = true;
        error = null;
        actualizarUI();
        
        viewLifecycleOwner.lifecycleScope.launch { 
            try {
                val resultado = repositorio.obtenerPeliculas();
                peliculas = resultado;
                peliculasFiltradas = resultado;
                cargando = false;
            }
            catch (e: Exception) {
                error = e.message;
                cargando = false;
            }
            finally {
                actualizarUI();
            }
        }
    }

    private fun actualizarUI() {
        // TODO Etapa 2e: implementa esta funcion
        //
        // Tiene que sincronizar la UI con el estado actual:
        //  - binding.progressBar.isVisible = cargando
        //  - binding.textError.isVisible = (error != null)
        //  - binding.textError.text = error o cadena vacia
        //  - adapter.submitList(peliculasFiltradas)
        //
        // Notar: vas a tener que llamar a esta funcion despues de cada cambio
        // de estado. Si te olvidas en algun lugar, la UI queda desincronizada.
        // Eso es exactamente lo que LiveData / StateFlow eliminan en el Lab 2A.
        
        binding.progressBar.isVisible = cargando
        binding.textError.isVisible = (error != null)
        binding.textError.text = error.orEmpty()
        adapter.submitList(peliculasFiltradas)
    }

    private fun actualizarBusqueda(consulta: String) {
        // TODO Etapa 3b: implementa esta funcion
        //
        // Filtra la lista de peliculas segun la consulta:
        //  - Si la consulta esta vacia: peliculasFiltradas = peliculas
        //  - Si no, filtrar por titulo ignorando mayusculas/minusculas:
        //      peliculas.filter { it.titulo.contains(consulta, ignoreCase = true) }
        //  - Llamar a actualizarUI() al final
        peliculasFiltradas = if (consulta.isEmpty()) {
            peliculas
        } 
        else {
            peliculas.filter { it.titulo.contains(consulta, ignoreCase = true) }
        }
        actualizarUI()
    }

    private fun navegarADetalle(pelicula: Pelicula) {
        // TODO Etapa 3c: implementa la navegacion al DetalleFragment
        //
        // Pasos:
        //  - Crear el Fragment de destino con DetalleFragment.newInstance(pelicula.id)
        //  - Hacer una transaccion con parentFragmentManager.beginTransaction()
        //  - Reemplazar el contenedor R.id.fragmentContainer con el nuevo Fragment 
        //  - Agregar la transaccion al back stack con addToBackStack(null)
        //  - Commitear la transaccion con commit()
        
        var fragment = DetalleFragment.newInstance(pelicula.id)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // evita fugas de memoria - no modificar
    }
}
