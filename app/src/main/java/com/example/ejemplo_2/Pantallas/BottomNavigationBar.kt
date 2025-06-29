package com.example.ejemplo_2.Pantallas

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        "Inicio" to "inventario",
        "Historial" to "historial",
        "Usuario" to "usuario"
    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(containerColor = Color(0xFF1E3A5F)) {
        items.forEach { (label, route) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = when (label) {
                            "Inicio" -> Icons.Default.Home
                            "Historial" -> Icons.Default.History
                            else -> Icons.Default.Person
                        },
                        contentDescription = label,
                        tint = Color.White
                    )
                },
                label = { Text(label, color = Color.White) }
            )
        }
    }
}
