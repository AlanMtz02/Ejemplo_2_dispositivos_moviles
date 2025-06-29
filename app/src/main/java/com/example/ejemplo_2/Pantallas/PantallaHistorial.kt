package com.example.ejemplo_2.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.ejemplo_2.componentes.TarjetaHistorialEntrada
import com.example.ejemplo_2.modelo.HistorialEntrada

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaHistorial(historial: List<HistorialEntrada>) {
    var filtroSeleccionado by remember { mutableStateOf("Todos") }
    var textoBusqueda by remember { mutableStateOf(TextFieldValue("")) }
    var comentarioMostrado by remember { mutableStateOf<String?>(null) }

    val opcionesFiltro = listOf("Todos", "En uso", "Entregados")
    val historialFiltrado = historial.filter {
        val coincideTexto = it.nombreHerramienta.contains(textoBusqueda.text, ignoreCase = true)
        val coincideEstado = when (filtroSeleccionado) {
            "En uso" -> !it.entregado
            "Entregados" -> it.entregado
            else -> true
        }
        coincideTexto && coincideEstado
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .background(Color(0xFFFFF5F5)) //Color de fondo de pantalla
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

            Row( //Contiene texto inventario, categoria e icono de filtro
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)){
                    Text(
                        text = "Historial",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A5F)
                        ),
                        modifier = Modifier.padding(top = 2.dp,bottom=4.dp)


                    )
                    Text(
                        text = "Estado: $filtroSeleccionado",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(top = 4.dp,bottom=4.dp)
                    )
                }

                var expanded by remember { mutableStateOf(false) }
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        opcionesFiltro.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    filtroSeleccionado = opcion
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            LazyColumn {
                items(historialFiltrado, key = { it.id }) { entrada ->
                    TarjetaHistorialEntrada(
                        entrada = entrada,
                        onClickComentario = { comentarioMostrado = it }
                    )
                }
            }

            comentarioMostrado?.let {
                AlertDialog( //Cuadro de dialogo
                    onDismissRequest = { comentarioMostrado = null },
                    title = { Text("Comentario") },
                    text = { Text(it.ifEmpty { "Sin comentarios registrados." }) },
                    confirmButton = {
                        TextButton(onClick = { comentarioMostrado = null }) {
                            Text("Cerrar")
                        }
                    }
                )
            }
        }
    }
}
