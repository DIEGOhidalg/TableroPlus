package com.example.tableroplus_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tableroplus_jetpackcompose.Navigation.MyApp
import com.example.tableroplus_jetpackcompose.ViewModel.ToDoViewModel
import com.example.tableroplus_jetpackcompose.Views.AddNewToDoScreen
import com.example.tableroplus_jetpackcompose.Views.ToDoListScreen
import com.example.tableroplus_jetpackcompose.ui.theme.TableroPlus_JetpackComposeTheme

class MainActivity : ComponentActivity() {
    lateinit var vm : Lazy<ToDoViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            vm = viewModels<ToDoViewModel>()
            TableroPlus_JetpackComposeTheme {
                MyApp(vm)

            }
        }
    }
}


