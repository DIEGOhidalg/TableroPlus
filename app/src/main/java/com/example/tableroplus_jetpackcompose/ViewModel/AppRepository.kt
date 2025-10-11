package com.example.tableroplus_jetpackcompose.ViewModel

import com.example.tableroplus_jetpackcompose.Model.ToDo

class AppRepository {

    private var todoList = mutableListOf<ToDo>(
        ToDo("Lavar Auto","20 de Mayo, 2025", false),
        ToDo("Hacer tarea de Full Stack","20 de Mayo, 2025", true),
    )

    fun getListOfToDo(): List<ToDo>{
        return todoList
    }

    fun updateToDo(index: Int, value: Boolean){
        todoList[index] = todoList[index].copy(isUrgent = value)
    }

    fun addNewTask(todo : ToDo){
        todoList.add(todo) //database,
    }

    fun deleteOneToDo(todelete: ToDo){
        todoList.remove(todelete)
    }

}