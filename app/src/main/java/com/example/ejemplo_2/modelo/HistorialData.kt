//Sirve para colocar las herramientas

package com.example.ejemplo_2.modelo

import java.util.*

fun historialEjemplo(): List<HistorialEntrada> {
    val ahora = Date()
    return listOf(
        HistorialEntrada(
            id = 1,
            nombreHerramienta = "Taladro Bosch",
            entregado = false,
            idEmpleado = "",
            fechaHoraUso = ahora,
            comentario = "En uso para ajustes en mantenimiento eléctrico."
        ),
        HistorialEntrada(
            id = 2,
            nombreHerramienta = "Multímetro",
            entregado = true,
            idEmpleado = "EMP-092",
            fechaHoraUso = ahora,
            comentario = "Devuelto sin observaciones."
        )
    )
}
