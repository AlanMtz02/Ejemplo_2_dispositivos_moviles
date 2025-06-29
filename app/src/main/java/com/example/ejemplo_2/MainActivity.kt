package com.example.ejemplo_2

import com.example.ejemplo_2.componentes.BottomNavigationBar
import com.example.ejemplo_2.Pantallas.PantallaInventario
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.ejemplo_2.Pantallas.*
import com.example.ejemplo_2.modelo.historialEjemplo
import com.example.ejemplo_2.ui.theme.Ejemplo_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ejemplo_2Theme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "inventario",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("inventario") {
                            PantallaInventario()
                        }
                        composable("historial") {
                            PantallaHistorial(historial = historialEjemplo()) //Con argumentos
                        }
                        composable("usuario") {
                            PantallaUsuario()
                        }
                    }
                }
            }
        }
    }
}

