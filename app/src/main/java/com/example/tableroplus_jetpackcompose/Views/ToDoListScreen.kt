package com.example.tableroplus_jetpackcompose.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tableroplus_jetpackcompose.Model.ToDo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    navController: NavController,
    list: SnapshotStateList<ToDo>,
    onswitch: (id: Int,value: Boolean)->Unit,
    ondelete: (todeleteToD: ToDo)->Unit,
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = {
            Text("Tablero Plus+")
        })} , floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("AddNewTodo")
            }) {
                Icon(Icons.Default.Add, contentDescription = "add new todo")
            }
        }){ innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
            items(count = list.toList().count()){ index->
                Column (modifier = Modifier.background(
                    if (list.toList()[index].isUrgent) Color.Red else Color.Green
                )){
                    Row (modifier = Modifier.fillMaxWidth().border(1.dp, Color.Black).padding(10.dp)){
                        Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                            Text(fontSize = 20.sp, text = list.toList()[index].task )
                            Spacer(Modifier.height(10.dp))
                            Text(fontSize = 20.sp, text = list.toList()[index].date)
                        }
                        Switch(checked =  list.toList()[index].isUrgent,
                            onCheckedChange = {it->
                                onswitch(index,it)
                            })
                        IconButton(onClick = {
                            ondelete(list.toList()[index])
                        }) {
                            Icon(imageVector = Icons.Default.Delete,
                                contentDescription = "Delete")
                        }
                    } }
            }
        }

    }
}