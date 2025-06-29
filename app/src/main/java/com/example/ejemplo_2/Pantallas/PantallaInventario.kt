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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.ejemplo_2.componentes.TarjetaHerramientaInventario
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
    var herramientaPendienteEliminar by remember { mutableStateOf<Herramienta?>(null) }


    val categorias = listOf("Todas", "Manuales", "Medición", "Eléctricas/Neumáticas", "Elevación y soporte", "Diagnóstico")

    val herramientasFiltradas = herramientas.filter {
        val coincideCategoria = categoriaSeleccionada == "Todas" || it.categoria == categoriaSeleccionada
        val coincideTexto = it.nombre.contains(textoBusqueda.text, ignoreCase = true)
        coincideCategoria && coincideTexto
    }

    Scaffold(
        containerColor = Color(0xFFE8F0FE), //Color de fondo
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
                .padding(bottom = padding.calculateBottomPadding())
                .fillMaxSize()
        ) {
            OutlinedTextField( //Barra de busqueda
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar herramienta") },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFFFFFF),     // Color cuando está enfocado
                    unfocusedContainerColor = Color(0xFFFFFFFF),   // Color cuando NO está enfocado
                    disabledContainerColor = Color.LightGray,      // Por se llegaa a desactivar
                    focusedIndicatorColor = Color(0xFF1E3A5F),     // Línea inferior al enfocar
                    unfocusedIndicatorColor = Color(0xFFBBBBBB)    // Línea inferior sin foco
                )

            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = "Inventario",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A5F)
                        ),
                        modifier=Modifier.padding(top = 2.dp, bottom = 4.dp)

                    )
                    Text(
                        text = "Categoría activa: $categoriaSeleccionada",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(top = 4.dp,bottom=4.dp)
                    )
                }

                Box { //Menu de categorias
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

                    TarjetaHerramientaInventario( //Llama a la funcion para mostrar las cards
                        herramienta = herramienta,

                        alEditar = {//Al presionar el icono de editar
                            herramientaEditando = it
                            mostrarDialogo = true
                        },
                        alEliminar = { //Al presionar el icono de eliminar
                            herramientaPendienteEliminar = herramienta
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
            //Cuadro de dialogo para eliminar herramienta
            herramientaPendienteEliminar?.let { herramienta ->
                AlertDialog(
                    onDismissRequest = { herramientaPendienteEliminar = null },
                    title = { Text("¿Eliminar herramienta?") },
                    text = { Text("¿Estás seguro de eliminar la herramienta \"${herramienta.nombre}\"?") },
                    confirmButton = {
                        TextButton(onClick = {
                            herramientas = herramientas.filterNot { it.id == herramienta.id }
                            herramientaPendienteEliminar = null
                        }) {
                            Text("Aceptar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { herramientaPendienteEliminar = null }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

        }
    }
}
