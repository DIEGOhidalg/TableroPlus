package com.example.tableroplus_jetpackcompose

import com.example.tableroplus_jetpackcompose.Model.ToDo
import com.example.tableroplus_jetpackcompose.ViewModel.ToDoViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ToDoViewModelTest {

    // Si ToDoViewModel NO usa viewModelScope, no necesitas la regla aquí.
    //Si la usa, descomenta las siguientes lineas:
    // @get:Rule
    // val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ToDoViewModel

    @Before
    fun setup() {
        viewModel = ToDoViewModel()
    }

    @Test
    fun `addNewTask agrega una tarea a la lista`() {
        // GIVEN
        val tareaInicial = viewModel.stateList.size
        val nuevaTarea = ToDo(
            task = "Haciendo Testing :O",
            isUrgent = true,
            date = "29/11/2025" //
        )

        // WHEN
        viewModel.addNewTask(nuevaTarea)

        // THEN
        assertEquals(tareaInicial + 1, viewModel.stateList.size)
        assertTrue(viewModel.stateList.contains(nuevaTarea))
    }

    @Test
    fun `deleteOneTask elimina la tarea correcta`() {
        // Agregamos una tarea para borrarla
        val tarea = ToDo(task = "Borrame :)", isUrgent = false, date = "29/11/2025")
        viewModel.addNewTask(tarea)
        assertTrue(viewModel.stateList.contains(tarea))

        // WHEN
        viewModel.deleteOneTask(tarea)

        // THEN
        assertFalse(viewModel.stateList.contains(tarea))
    }
}