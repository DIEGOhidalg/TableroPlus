package com.example.tableroplus_jetpackcompose.Views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tableroplus_jetpackcompose.ViewModel.UsuarioViewModel

@Composable
fun RoutingScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

    // Este LaunchedEffect se ejecutará cuando 'estado.isLoading' cambie
    LaunchedEffect(estado.isLoading) {

        // Solo actuamos cuando la carga haya terminado
        if (!estado.isLoading) {

            // DECISIÓN: Si el nombre y correo NO están vacíos, vamos a la lista
            val destination = if (estado.nombre.isNotBlank() && estado.correo.isNotBlank()) {
                "ListOfTodos"
            } else {
                "registro"
            }

            // Navegamos a la pantalla decidida
            navController.navigate(destination) {
                // Importante: borramos la pantalla "router" del historial.
                // Si el usuario da "atrás", no vuelve aquí.
                popUpTo("router") { inclusive = true }
            }
        }
    }

    // Mientras 'isLoading' es true, mostramos un indicador de carga
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (estado.isLoading) {
            CircularProgressIndicator()
            // Aquí podrías poner tu logo si quisieras
        }
    }
}