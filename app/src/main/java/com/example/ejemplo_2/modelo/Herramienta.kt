package com.example.ejemplo_2.modelo

// Modelo de datos que representa una herramienta
data class Herramienta(
    val id: Int,
    var nombre: String,
    var categoria: String,
    var stock: Int,
    var imagenUrl: String = "",

    var descripcion:String=""
)
