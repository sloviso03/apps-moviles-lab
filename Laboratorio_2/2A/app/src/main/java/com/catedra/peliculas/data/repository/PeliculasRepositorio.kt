package com.catedra.peliculas.data.repository

import com.catedra.peliculas.data.model.Pelicula
import kotlinx.coroutines.delay

/**
 * Repositorio de peliculas.
 * En una app real, esta clase haria llamadas a una API o base de datos.
 * Para este lab, devuelve datos hardcodeados con un delay simulado de red.
 *
 * No modificar este archivo.
 */
class PeliculasRepositorio {

    suspend fun obtenerPeliculas(): List<Pelicula> {
        // Simulamos latencia de red de 1 segundo
        delay(1000)
        return PELICULAS
    }

    suspend fun obtenerPelicula(id: String): Pelicula? {
        delay(500)
        return PELICULAS.find { it.id == id }
    }

    companion object {
        private val PELICULAS = listOf(
            Pelicula(
                id = "1",
                titulo = "Inception",
                anio = 2010,
                genero = "Ciencia ficcion",
                descripcion = "Un ladron que roba secretos corporativos a traves del uso de la tecnologia de compartir suenos recibe la tarea inversa de plantar una idea en la mente de un CEO.",
                director = "Christopher Nolan",
                duracionMinutos = 148
            ),
            Pelicula(
                id = "2",
                titulo = "The Godfather",
                anio = 1972,
                genero = "Drama",
                descripcion = "El envejecido patriarca de una dinastia del crimen organizado transfiere el control de su imperio clandestino a su renuente hijo.",
                director = "Francis Ford Coppola",
                duracionMinutos = 175
            ),
            Pelicula(
                id = "3",
                titulo = "Interstellar",
                anio = 2014,
                genero = "Ciencia ficcion",
                descripcion = "Un equipo de exploradores viaja a traves de un agujero de gusano en el espacio en un intento de asegurar la supervivencia de la humanidad.",
                director = "Christopher Nolan",
                duracionMinutos = 169
            ),
            Pelicula(
                id = "4",
                titulo = "Pulp Fiction",
                anio = 1994,
                genero = "Crimen",
                descripcion = "Las vidas de dos sicarios, un boxeador, una esposa de gangster y un par de bandidos se entrelazan en cuatro historias de violencia y redencion.",
                director = "Quentin Tarantino",
                duracionMinutos = 154
            ),
            Pelicula(
                id = "5",
                titulo = "The Dark Knight",
                anio = 2008,
                genero = "Accion",
                descripcion = "Cuando el Joker emerge desde su misterioso pasado y siembra el caos, Batman debe aceptar una de las pruebas psicologicas y fisicas mas grandes de su capacidad.",
                director = "Christopher Nolan",
                duracionMinutos = 152
            ),
            Pelicula(
                id = "6",
                titulo = "Forrest Gump",
                anio = 1994,
                genero = "Drama",
                descripcion = "Las presidencias de Kennedy y Johnson, la guerra de Vietnam y otros eventos historicos se desarrollan a traves de la perspectiva de un hombre de Alabama con un coeficiente intelectual de 75.",
                director = "Robert Zemeckis",
                duracionMinutos = 142
            ),
            Pelicula(
                id = "7",
                titulo = "Parasite",
                anio = 2019,
                genero = "Thriller",
                descripcion = "La codicia y el conflicto de clases amenazan la nueva relacion simbiotica entre la rica familia Park y el clan empobrecido de los Kim.",
                director = "Bong Joon-ho",
                duracionMinutos = 132
            ),
            Pelicula(
                id = "8",
                titulo = "The Matrix",
                anio = 1999,
                genero = "Ciencia ficcion",
                descripcion = "Un hacker descubre la verdad sobre su realidad y su rol en la guerra contra sus controladores.",
                director = "Lana y Lilly Wachowski",
                duracionMinutos = 136
            )
        )
    }
}
