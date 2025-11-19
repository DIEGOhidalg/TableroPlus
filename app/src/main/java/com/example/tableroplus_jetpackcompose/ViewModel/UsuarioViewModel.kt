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



class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    // ESTADO INTERNO MUTABLE
    private val _estado = MutableStateFlow(UsuarioUiState()) // <-- isLoading es true por defecto
    val estado: StateFlow<UsuarioUiState> = _estado

    private val dataStoreRepository = UserDataStoreRepository(application)

    init {

        viewModelScope.launch {
            // Ahora recibimos un objeto 'it' que es de tipo DatosUsuario
            dataStoreRepository.userDataFlow.first().let { datos ->
                _estado.update {
                    it.copy(
                        nombre = datos.nombre,
                        correo = datos.correo,
                        clave = datos.clave,
                        imagenUri = datos.imagenUri,
                        isLoading = false
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

    fun onImagenUriChange(uriString: String) {
        _estado.update { it.copy(imagenUri = uriString) }
    }

    // 4. NUEVA FUNCIÓN: La llamaremos desde la UI para guardar
    fun guardarUsuario() {
        val estadoActual = _estado.value

        viewModelScope.launch {
            dataStoreRepository.saveUserData(
                nombre = estadoActual.nombre,
                correo = estadoActual.correo,
                clave = estadoActual.clave,
                imagenUri = estadoActual.imagenUri
            )
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