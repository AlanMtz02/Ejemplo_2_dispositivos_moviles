package com.example.ejemplo_2.modelo

import java.util.*

data class HistorialEntrada(
    val id: Int,
    val nombreHerramienta: String,
    val entregado: Boolean,
    val idEmpleado: String,
    val fechaHoraUso: Date,
    val comentario: String
)
