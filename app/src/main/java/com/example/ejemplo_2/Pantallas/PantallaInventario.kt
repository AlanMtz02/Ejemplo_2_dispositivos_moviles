package com.example.ejemplo_2.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.ejemplo_2.modelo.Herramienta

fun herramientasEjemplo(): List<Herramienta> = listOf(
    Herramienta(1, "Taladro Bosch", "Eléctricas/Neumáticas", 10, "", ""),
    Herramienta(2, "Martillo Stanley", "Manuales", 20, "", ""),
    Herramienta(3, "Nivel de burbuja", "Medición", 12, "", ""),
    Herramienta(4, "Multímetro", "Diagnóstico", 5, "", ""),
    Herramienta(5, "Gato hidráulico", "Elevación y soporte", 3, "", "")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInventario() {
    var herramientas by remember { mutableStateOf(herramientasEjemplo()) }
    var textoBusqueda by remember { mutableStateOf(TextFieldValue("")) }
    var categoriaSeleccionada by remember { mutableStateOf("Todas") }
    var filtroExpandido by remember { mutableStateOf(false) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var herramientaEditando by remember { mutableStateOf<Herramienta?>(null) }

    val categorias = listOf("Todas", "Manuales", "Medición", "Eléctricas/Neumáticas", "Elevación y soporte", "Diagnóstico")

    val herramientasFiltradas = herramientas.filter {
        val coincideCategoria = categoriaSeleccionada == "Todas" || it.categoria == categoriaSeleccionada
        val coincideTexto = it.nombre.contains(textoBusqueda.text, ignoreCase = true)
        coincideCategoria && coincideTexto
    }

    Scaffold(
        containerColor = Color(0xFFFFF5F5),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                herramientaEditando = null
                mostrarDialogo = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar herramienta")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(0xFFE8F0FE))
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar herramienta") },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFF5F5),     // Color cuando está enfocado
                    unfocusedContainerColor = Color(0xFFFFF5F5),   // Color cuando NO está enfocado
                    disabledContainerColor = Color.LightGray,      // Por se llegaa a desactivar
                    focusedIndicatorColor = Color(0xFF1E3A5F),     // Línea inferior al enfocar
                    unfocusedIndicatorColor = Color(0xFFBBBBBB)    // Línea inferior sin foco

            )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "Inventario",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A5F)
                        )
                    )
                    Text(
                        text = "Categoría activa: $categoriaSeleccionada",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(top = 8.dp,bottom=8.dp)
                    )
                }

                Box {
                    IconButton(onClick = { filtroExpandido = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
                    }
                    DropdownMenu(
                        expanded = filtroExpandido,
                        onDismissRequest = { filtroExpandido = false }
                    ) {
                        categorias.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    categoriaSeleccionada = cat
                                    filtroExpandido = false
                                }
                            )
                        }
                    }
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(herramientasFiltradas, key = { it.id }) { herramienta ->
                    TarjetaHerramienta(
                        herramienta = herramienta,
                        alEditar = {
                            herramientaEditando = it
                            mostrarDialogo = true
                        },
                        alEliminar = {
                            herramientas = herramientas.filterNot { h -> h.id == herramienta.id }
                        }
                    )
                }
            }

            if (mostrarDialogo) {
                DialogoHerramienta(
                    herramienta = herramientaEditando,
                    alCerrar = { mostrarDialogo = false },
                    alGuardar = { herramientaActualizada ->
                        herramientas = if (herramientaActualizada.id in herramientas.map { it.id }) {
                            herramientas.map {
                                if (it.id == herramientaActualizada.id) herramientaActualizada else it
                            }
                        } else {
                            herramientas + herramientaActualizada.copy(
                                id = (herramientas.maxOfOrNull { it.id } ?: 0) + 1
                            )
                        }
                        mostrarDialogo = false
                    }
                )
            }
        }
    }
}
