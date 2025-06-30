package com.example.ejemplo_2.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Construction
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
import com.example.ejemplo_2.modelo.HistorialEntrada
import java.text.SimpleDateFormat
import java.util.*

//Usuario jefe-menu inventario
@Composable
fun TarjetaHerramientaInventarioNO(
    herramienta: Herramienta,
    alEditar: (Herramienta) -> Unit,
    alEliminar: (Herramienta) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)), //Color de la card
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

//Usuario jefe-menu historial
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
            //Diseño para el boton comentarios
            if (!entrada.comentario.isNullOrBlank()) { //Si hay comentarios, entonces si mostrara el boton
                Button(
                    onClick = { onClickComentario(entrada.comentario) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1C42)),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comentario",
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Comentario", color = Color.White)
                }
            }

        }
    }
}

@Composable
fun TarjetaHerramientaInventario(
    herramienta: Herramienta,
    alEditar: (Herramienta) -> Unit,
    alEliminar: (Herramienta) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                Color(0xFF2E5A88),
                shape = RoundedCornerShape(12.dp)
            ), //Color, ancho
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), //Sombra de la card
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)), //Color de la card
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(herramienta.nombre, style = MaterialTheme.typography.titleMedium)
                Text(
                    "Disponible: ${herramienta.stock}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            //Coloca los botones uno debajo del otro. Diseño de los botones editar y eliminar
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.width(IntrinsicSize.Max)
            ) {
                Button(
                    onClick = { alEditar(herramienta) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar")
                }

                Button(
                    onClick = { alEliminar(herramienta) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}


fun Date.formatear(): String {
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formato.format(this)
}
