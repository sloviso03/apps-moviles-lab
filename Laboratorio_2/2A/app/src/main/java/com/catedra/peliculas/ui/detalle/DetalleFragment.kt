package com.catedra.peliculas.ui.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.catedra.peliculas.data.repository.PeliculasRepositorio
import com.catedra.peliculas.databinding.FragmentDetalleBinding
import kotlinx.coroutines.launch

/**
 * Pantalla de detalle de una pelicula.
 *
 * Este Fragment esta COMPLETO. Usalo como referencia para entender:
 *  - El patron newInstance() para pasar datos al Fragment via Bundle
 *  - El patron _binding nullable para ViewBinding en Fragments
 *  - El uso de lifecycleScope para invocar funciones suspend
 *
 * No modificar este archivo.
 */
class DetalleFragment : Fragment() {

    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!

    // Repositorio - el mismo que usas en PeliculasFragment
    private val repositorio = PeliculasRepositorio()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recuperamos el id de la pelicula desde los argumentos del Fragment
        val peliculaId = arguments?.getString(ARG_PELICULA_ID)

        if (peliculaId == null) {
            binding.textError.text = getString(com.catedra.peliculas.R.string.error_pelicula_no_encontrada)
            binding.textError.visibility = View.VISIBLE
            binding.contenido.visibility = View.GONE
            return
        }

        cargarDetalle(peliculaId)
    }

    private fun cargarDetalle(id: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.contenido.visibility = View.GONE
        binding.textError.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val pelicula = repositorio.obtenerPelicula(id)

                binding.progressBar.visibility = View.GONE

                if (pelicula == null) {
                    binding.textError.text = getString(com.catedra.peliculas.R.string.error_pelicula_no_encontrada)
                    binding.textError.visibility = View.VISIBLE
                } else {
                    binding.textTitulo.text = pelicula.titulo
                    binding.textInfo.text = "${pelicula.anio} \u00b7 ${pelicula.genero} \u00b7 ${pelicula.duracionMinutos} min"
                    binding.textDirector.text = getString(com.catedra.peliculas.R.string.detalle_director, pelicula.director)
                    binding.textDescripcion.text = pelicula.descripcion
                    binding.contenido.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                binding.textError.text = e.message ?: getString(com.catedra.peliculas.R.string.error_generico)
                binding.textError.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PELICULA_ID = "peliculaId"

        /**
         * Crea una nueva instancia de DetalleFragment con el id de la pelicula
         * adjuntado en los argumentos. Este patron es la forma estandar de pasar
         * datos a un Fragment en el modelo tradicional.
         */
        fun newInstance(peliculaId: String): DetalleFragment {
            return DetalleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PELICULA_ID, peliculaId)
                }
            }
        }
    }
}
