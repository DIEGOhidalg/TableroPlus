package com.example.tableroplus_jetpackcompose.Navigation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tableroplus_jetpackcompose.ViewModel.ToDoViewModel
import com.example.tableroplus_jetpackcompose.ViewModel.UsuarioViewModel
import com.example.tableroplus_jetpackcompose.Views.AddNewToDoScreen
import com.example.tableroplus_jetpackcompose.Views.EditToDoScreen
import com.example.tableroplus_jetpackcompose.Views.RegistroScreen
import com.example.tableroplus_jetpackcompose.Views.RoutingScreen
import com.example.tableroplus_jetpackcompose.Views.ToDoListScreen
import com.example.tableroplus_jetpackcompose.Views.EditarScreen
import com.example.tableroplus_jetpackcompose.Views.PerfilScreen



@Composable
fun MyApp(vm: Lazy<ToDoViewModel>){
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val toDoViewModel: ToDoViewModel = viewModel()

    NavHost(navController = navController, startDestination = "router" ) {

        composable("router") {
            RoutingScreen(navController, usuarioViewModel)
        }


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

        composable(
            route = "EditTodo/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            val toDo = toDoViewModel.stateList[index]

            EditToDoScreen(
                navController = navController,
                toDo = toDo,
                index = index,
                onSave = { idx, newToDo ->
                    toDoViewModel.editTask(idx, newToDo)
                }
            )
        }

        composable("EditarScreen") {
            EditarScreen(navController, usuarioViewModel)
        }


        composable("PerfilScreen") {
            PerfilScreen(navController, usuarioViewModel)
        }



    }
}






