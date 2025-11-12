package com.example.tableroplus_jetpackcompose.ViewModel

import androidx.lifecycle.ViewModel
import com.example.tableroplus_jetpackcompose.Model.*
import com.example.tableroplus_jetpackcompose.Repository.UserDataStoreRepository
import kotlinx.coroutines.flow.*

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tableroplus_jetpackcompose.Model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// 1. CAMBIO: Hereda de AndroidViewModel(application) en lugar de ViewModel()
// En UsuarioViewModel.kt

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    // ESTADO INTERNO MUTABLE
    private val _estado = MutableStateFlow(UsuarioUiState()) // <-- isLoading es true por defecto
    val estado: StateFlow<UsuarioUiState> = _estado

    private val dataStoreRepository = UserDataStoreRepository(application)

    init {
        // Modificamos este bloque
        viewModelScope.launch {
            // Usamos .first() para tomar solo el primer valor emitido (los datos guardados)
            dataStoreRepository.userDataFlow.first()
                .let { (nombre, correo) ->

                    // Actualizamos el estado con los datos cargados
                    _estado.update {
                        it.copy(
                            nombre = nombre,
                            correo = correo,
                            isLoading = false // <-- ¡Marcamos la carga como finalizada!
                        )
                    }
                }
        }
    }
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    // 4. NUEVA FUNCIÓN: La llamaremos desde la UI para guardar
    fun guardarUsuario() {
        val estadoActual = _estado.value

        // Lanzamos una coroutine en el scope del ViewModel
        viewModelScope.launch {
            dataStoreRepository.saveUserData(
                nombre = estadoActual.nombre,
                correo = estadoActual.correo
            )
            // NO guardamos la clave.
        }
    }


    fun validarFormulario(): Boolean {
        // ... (Tu función de validación no necesita cambios)
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