package com.example.tableroplus_jetpackcompose.ViewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tableroplus_jetpackcompose.Model.ToDo
import com.example.tableroplus_jetpackcompose.Model.UsuarioErrores
import kotlinx.coroutines.flow.update

class ToDoViewModel : ViewModel() {

    var repo = AppRepository() // source of truth

    var stateList = mutableStateListOf<ToDo>().apply {
        addAll(repo.getListOfToDo())
    }

    fun addNewTask(todo : ToDo) {
        repo.addNewTask(todo)
        stateList.add(todo)

    }

    fun updateOneTask(index : Int, value: Boolean) {
        repo.updateToDo(index,value)
        stateList[index] = stateList[index].copy(isUrgent = value)
    }

    fun deleteOneTask(toDo: ToDo){
        repo.deleteOneToDo(toDo)
        stateList.remove(toDo)
    }




}
