package com.catedra.peliculas.data.model

/**
 * Modelo de datos del dominio.
 * Representa una pelicula con todos sus atributos.
 *
 * No modificar este archivo.
 */
data class Pelicula(
    val id: String,
    val titulo: String,
    val anio: Int,
    val genero: String,
    val descripcion: String,
    val director: String,
    val duracionMinutos: Int
)
