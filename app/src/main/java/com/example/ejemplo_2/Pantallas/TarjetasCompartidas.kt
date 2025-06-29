package com.example.ejemplo_2.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ejemplo_2.modelo.Herramienta
import com.example.ejemplo_2.modelo.HistorialEntrada
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TarjetaHerramientaInventario(
    herramienta: Herramienta,
    alEditar: (Herramienta) -> Unit,
    alEliminar: (Herramienta) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF1E3A5F))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = herramienta.nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = "Categoría: ${herramienta.categoria}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Text(text = "Stock: ${herramienta.stock}", style = MaterialTheme.typography.labelSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { alEditar(herramienta) }) { Text("Editar") }
                TextButton(onClick = { alEliminar(herramienta) }) { Text("Eliminar") }
            }
        }
    }
}

@Composable
fun TarjetaHistorialEntrada(
    entrada: HistorialEntrada,
    onClickComentario: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)), //Color de la card
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF8B1C42)), //Color del borde
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (entrada.entregado) Icons.Default.CheckCircle else Icons.Default.Construction,
                contentDescription = null,
                tint = if (entrada.entregado) Color(0xFF2E7D32) else Color(0xFFF57F17),
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = entrada.nombreHerramienta, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = if (entrada.entregado)
                        "Entregado por : ${entrada.idEmpleado}"
                    else
                        " En uso por : ${entrada.idEmpleado}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(text = entrada.fechaHoraUso.formatear(), style = MaterialTheme.typography.labelSmall)
            }
            IconButton(onClick = { onClickComentario(entrada.comentario) }) {
                Icon(
                    imageVector = Icons.Default.ChatBubbleOutline,
                    contentDescription = "Comentario",
                    tint = Color(0xFF000000) //Color del icono
                )
            }
        }
    }
}

fun Date.formatear(): String {
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formato.format(this)
}
