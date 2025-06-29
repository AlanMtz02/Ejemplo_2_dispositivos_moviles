package com.example.ejemplo_2.Pantallas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ejemplo_2.modelo.Herramienta

@Composable
fun TarjetaHerramienta(
    herramienta: Herramienta,
    alEditar: (Herramienta) -> Unit,
    alEliminar: (Herramienta) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(width =1.dp, Color(0xFF2E5A88), shape = RoundedCornerShape(12.dp)), //Color, ancho
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), //Sombra de la card
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)), //Color de la card
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(herramienta.imagenUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen herramienta",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(herramienta.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Disponible: ${herramienta.stock}", style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                IconButton(onClick = { alEditar(herramienta) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { alEliminar(herramienta) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}
