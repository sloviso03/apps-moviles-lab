package com.catedra.peliculas.ui.peliculas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.catedra.peliculas.data.model.Pelicula
import com.catedra.peliculas.databinding.ItemPeliculaBinding

/**
 * Adapter del RecyclerView para mostrar la lista de peliculas.
 * Extiende ListAdapter, que internamente usa DiffUtil para actualizar
 * solo los elementos que cambiaron cuando se llama a submitList().
 *
 * No modificar este archivo.
 */
class PeliculaAdapter(
    private val onClick: (Pelicula) -> Unit
) : ListAdapter<Pelicula, PeliculaAdapter.PeliculaViewHolder>(PeliculaDiffCallback) {

    inner class PeliculaViewHolder(
        private val binding: ItemPeliculaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pelicula: Pelicula) {
            binding.textTitulo.text = pelicula.titulo
            binding.textInfo.text = "${pelicula.anio} \u00b7 ${pelicula.genero}"
            binding.root.setOnClickListener { onClick(pelicula) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val binding = ItemPeliculaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PeliculaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object PeliculaDiffCallback : DiffUtil.ItemCallback<Pelicula>() {
        override fun areItemsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean =
            oldItem == newItem
    }
}
