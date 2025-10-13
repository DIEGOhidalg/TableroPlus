package com.example.tableroplus_jetpackcompose.Model


data class UsuarioUiState (
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val errores: UsuarioErrores = UsuarioErrores(),
)