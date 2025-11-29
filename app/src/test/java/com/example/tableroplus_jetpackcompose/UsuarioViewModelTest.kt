package com.example.tableroplus_jetpackcompose

import android.app.Application
import com.example.tableroplus_jetpackcompose.ViewModel.UsuarioViewModel
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UsuarioViewModelTest {

    // 1. Aplicamos la regla de corutinas
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // 2. Variables para el test
    private lateinit var viewModel: UsuarioViewModel
    private lateinit var applicationMock: Application

    @Before
    fun setup() {
        // Simulamos la Application (relaxed = true evita errores si se llama a algo no definido)
        applicationMock = mockk(relaxed = true)

        // Inicializamos el ViewModel con el mock
        viewModel = UsuarioViewModel(applicationMock)
    }

    @Test
    fun `onNombreChange actualiza el estado correctamente`() {
        // GIVEN (Dado un valor)
        val nuevoNombre = "Juan Perez"

        // WHEN (Cuando llamamos a la función)
        viewModel.onNombreChange(nuevoNombre)

        // THEN (Entonces el estado debe reflejarlo)
        assertEquals(nuevoNombre, viewModel.estado.value.nombre)
        // Y el error de nombre debe ser nulo
        assertNull(viewModel.estado.value.errores.nombre)
    }

    @Test
    fun `validarFormulario falla con campos vacios`() {
        // GIVEN: Estado inicial vacío
        viewModel.onNombreChange("")
        viewModel.onCorreoChange("")
        viewModel.onClaveChange("")

        // WHEN
        val esValido = viewModel.validarFormulario()

        // THEN
        assertFalse(esValido) // Debe ser falso
        assertNotNull(viewModel.estado.value.errores.nombre) // Debe haber error en nombre
        assertEquals("Campo Obligatorio", viewModel.estado.value.errores.nombre)
    }

    @Test
    fun `validarFormulario pasa con datos correctos`() {
        // GIVEN: Llenamos los datos correctamente
        viewModel.onNombreChange("Maria")
        viewModel.onCorreoChange("maria@test.com")
        viewModel.onClaveChange("123456") // 6 caracteres mínimo

        // WHEN
        val esValido = viewModel.validarFormulario()

        // THEN
        assertTrue(esValido) // Debe ser verdadero
        assertNull(viewModel.estado.value.errores.nombre) // Sin errores
    }

    @Test
    fun `validarFormulario detecta correo invalido`() {
        // GIVEN
        viewModel.onCorreoChange("correo_sin_arroba")

        // WHEN
        val esValido = viewModel.validarFormulario()

        // THEN
        assertFalse(esValido)
        assertEquals("Correo Invalido", viewModel.estado.value.errores.correo)
    }
}