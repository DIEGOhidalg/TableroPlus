package com.example.tableroplus_jetpackcompose.ViewModel

import com.example.tableroplus_jetpackcompose.Model.ToDo

class AppRepository {

    private var todoList = mutableListOf<ToDo>(
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

    fun editTask(index: Int, newTask: ToDo) {
        todoList[index] = newTask
    }

}