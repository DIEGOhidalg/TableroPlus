package com.example.tableroplus_jetpackcompose.Model


// En tu archivo de Model (donde está UsuarioUiState)
data class UsuarioUiState (
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val errores: UsuarioErrores = UsuarioErrores(),
    val isLoading: Boolean = true,
    val imagenUri: String = ""
)