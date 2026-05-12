package com.catedra.miprimeraappcompose

sealed class Screen{
    object Calculator : Screen()
    data class Result(val amount: Double, val percentage: Int) : Screen()
}