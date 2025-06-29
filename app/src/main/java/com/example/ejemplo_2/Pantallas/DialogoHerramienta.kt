package com.example.ejemplo_2.Pantallas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ejemplo_2.modelo.Herramienta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoHerramienta(
    herramienta: Herramienta?,
    alCerrar: () -> Unit,
    alGuardar: (Herramienta) -> Unit
) {
    val categorias = listOf(
        "Manuales", "Medición", "Eléctricas/Neumáticas", "Elevación y soporte", "Diagnóstico"
    )

    val contexto = LocalContext.current
    var nombre by remember { mutableStateOf(herramienta?.nombre ?: "") }
    var categoriaSeleccionada by remember { mutableStateOf(herramienta?.categoria ?: categorias[0]) }
    var stockTexto by remember { mutableStateOf(herramienta?.stock?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(herramienta?.descripcion ?: "") }
    var uriSeleccionada by remember { mutableStateOf<Uri?>(herramienta?.imagenUrl?.let { Uri.parse(it) }) }
    var expanded by remember { mutableStateOf(false) }

    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uriSeleccionada = it }
    )

    AlertDialog(
        onDismissRequest = alCerrar,
        title = { Text(if (herramienta == null) "Agregar herramienta" else "Editar herramienta") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = categoriaSeleccionada,
                        onValueChange = {},
                        label = { Text("Categoría") },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categorias.forEach { categoria ->
                            DropdownMenuItem(
                                text = { Text(categoria) },
                                onClick = {
                                    categoriaSeleccionada = categoria
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = stockTexto,
                    onValueChange = { stockTexto = it },
                    label = { Text("Stock") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción (opcional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    singleLine = false,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { lanzadorGaleria.launch("image/*") }) {
                    Text("Seleccionar imagen desde galería")
                }

                uriSeleccionada?.let {
                    AsyncImage(
                        model = ImageRequest.Builder(contexto)
                            .data(it)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Vista previa",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(100.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val stock = stockTexto.toIntOrNull() ?: 0
                alGuardar(
                    Herramienta(
                        id = herramienta?.id ?: -1,
                        nombre = nombre,
                        categoria = categoriaSeleccionada,
                        stock = stock,
                        imagenUrl = uriSeleccionada?.toString() ?: "",
                        descripcion = descripcion
                    )
                )
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = alCerrar) {
                Text("Cancelar")
            }
        }
    )
}
