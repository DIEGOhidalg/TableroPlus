package com.example.tableroplus_jetpackcompose.ViewModel

import androidx.lifecycle.ViewModel
import com.example.tableroplus_jetpackcompose.Model.*
import kotlinx.coroutines.flow.*


class UsuarioViewModel : ViewModel() {

    // ESTADO INTERNO MUTABLE
    private val _estado = MutableStateFlow(UsuarioUiState())

    val estado: StateFlow<UsuarioUiState> = _estado

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }



    fun validarFormulario(): Boolean {

        val estadoActual = _estado.value

        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo Obligatorio" else null,
            correo = if (!estadoActual.correo.contains( "@")) "Correo Invalido" else null,
            clave = if (estadoActual.clave.length < 6) "Debe tener al menos 6 caracteres " else null,
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,

        ).isNotEmpty()

        _estado.update { it.copy(errores = errores)}

        return !hayErrores

    }

}