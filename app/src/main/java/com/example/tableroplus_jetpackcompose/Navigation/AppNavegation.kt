package com.example.tableroplus_jetpackcompose.Navigation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tableroplus_jetpackcompose.ViewModel.ToDoViewModel
import com.example.tableroplus_jetpackcompose.ViewModel.UsuarioViewModel
import com.example.tableroplus_jetpackcompose.Views.AddNewToDoScreen
import com.example.tableroplus_jetpackcompose.Views.RegistroScreen
import com.example.tableroplus_jetpackcompose.Views.ToDoListScreen


@Composable
fun MyApp(vm: Lazy<ToDoViewModel>){
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    NavHost(navController = navController, startDestination = "registro" ) {

        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        composable("ListOfTodos") {
            // App main UI
            ToDoListScreen(navController, usuarioViewModel, vm.value.stateList, onswitch = { id, value ->  vm.value.updateOneTask(id,value) }, ondelete = { todeletetask -> vm.value.deleteOneTask(todeletetask) } )
        }
        composable("AddNewTodo") {
            // add new task UI
            AddNewToDoScreen(navController, onSave = {toadd ->
                vm.value.addNewTask(toadd)
            })
        }
    }
}






