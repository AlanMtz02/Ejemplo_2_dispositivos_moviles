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
        ),
        HistorialEntrada(
            id=3,
            nombreHerramienta = "Herramienta",
            entregado = false,
            idEmpleado = "ID falta",
            fechaHoraUso = ahora,
            comentario = ""
        ),
        HistorialEntrada(
            id=4,
            nombreHerramienta = "Herramienta",
            entregado = false,
            idEmpleado = "ID falta",
            fechaHoraUso = ahora,
            comentario = ""
        ),
        HistorialEntrada(
            id=5,
            nombreHerramienta = "Herramienta",
            entregado = false,
            idEmpleado = "ID falta",
            fechaHoraUso = ahora,
            comentario = ""
        ),
        HistorialEntrada(
            id=6,
            nombreHerramienta = "Herramienta",
            entregado = false,
            idEmpleado = "ID falta",
            fechaHoraUso = ahora,
            comentario = ""
        ),
        HistorialEntrada(
            id=7,
            nombreHerramienta = "Herramienta",
            entregado = false,
            idEmpleado = "ID falta",
            fechaHoraUso = ahora,
            comentario = ""
        ),
        HistorialEntrada(
            id=8,
            nombreHerramienta = "Herramienta",
            entregado = false,
            idEmpleado = "ID falta",
            fechaHoraUso = ahora,
            comentario = ""
        ),
        HistorialEntrada(
            id=9,
            nombreHerramienta = "Herramienta",
            entregado = false,
            idEmpleado = "ID falta",
            fechaHoraUso = ahora,
            comentario = ""
        ),
        HistorialEntrada(
            id=10,
            nombreHerramienta = "Herramienta",
            entregado = false,
            idEmpleado = "ID falta",
            fechaHoraUso = ahora,
            comentario = ""
        ),
        HistorialEntrada(
            id=11,
            nombreHerramienta = "Herramienta",
            entregado = false,
            idEmpleado = "ID falta",
            fechaHoraUso = ahora,
            comentario = ""
        ),

    )
}
