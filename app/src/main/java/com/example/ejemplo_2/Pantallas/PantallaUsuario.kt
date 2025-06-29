package com.example.ejemplo_2.Pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaUsuario() {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Text(
                text = "Pantalla de Usuario",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}
