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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tableroplus_jetpackcompose.Model.ToDo
import com.example.tableroplus_jetpackcompose.Model.WeatherUiState
import com.example.tableroplus_jetpackcompose.ViewModel.UsuarioViewModel
import com.example.tableroplus_jetpackcompose.ViewModel.WeatherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.size


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    navController: NavController,
    viewModel: UsuarioViewModel,
    list: SnapshotStateList<ToDo>,
    onswitch: (id: Int, value: Boolean) -> Unit,
    ondelete: (todeleteToD: ToDo) -> Unit,
) {
    val estado by viewModel.estado.collectAsState()

    // WeatherViewModel + carga de clima
    val weatherViewModel: WeatherViewModel = viewModel()
    val weatherUiState = weatherViewModel.weatherUiState

    LaunchedEffect(Unit) {
        weatherViewModel.loadWeather()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Tablero Plus+",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            " Bienvenido ${estado.nombre}. Estas son tus tareas: ",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // ⚠️ CORRECCIÓN IMPORTANTE:
                        // Cambiamos "PerfilScreen" por "registro" para que no se crashee
                        navController.navigate("PerfilScreen")
                    }) {
                        // Verificamos si hay una URI de imagen guardada
                        if (estado.imagenUri.isNotEmpty()) {
                            AsyncImage(
                                model = estado.imagenUri,
                                contentDescription = "Foto de Perfil",
                                modifier = Modifier
                                    .size(36.dp) // Tamaño ideal para la barra superior
                                    .clip(CircleShape), // La recortamos en círculo
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            // Si no hay foto, mostramos el ícono por defecto
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Modificar Perfil"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("AddNewTodo")
            }) {
                Icon(Icons.Default.Add, contentDescription = "add new todo")
            }
        }
    ) { innerPadding ->

        LazyColumn(Modifier.padding(innerPadding)) {

            // HEADER DE CLIMA
            item {
                WeatherHeader(weatherUiState)
            }

            items(count = list.toList().count()) { index ->
                Column(
                    modifier = Modifier
                        .background(
                            if (list.toList()[index].isUrgent)
                                Color.hsv(0f, 0.55f, 1f)
                            else
                                Color.hsv(230f, 0.20f, 1f),
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                Color.hsv(230f, 0.20f, 0.72f),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                            Text(fontSize = 20.sp, text = list.toList()[index].task)
                            Spacer(Modifier.height(10.dp))
                            Text(fontSize = 20.sp, text = list.toList()[index].date)
                        }

                        Switch(
                            checked = list.toList()[index].isUrgent,
                            onCheckedChange = { isChecked ->
                                onswitch(index, isChecked)
                            }
                        )

                        IconButton(onClick = {
                            navController.navigate("EditTodo/$index")
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit"
                            )
                        }

                        IconButton(onClick = {
                            ondelete(list.toList()[index])
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherHeader(state: WeatherUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Clima actual en Santiago",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(4.dp))

            when {
                state.isLoading -> {
                    Text(
                        text = "Cargando clima...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                state.errorMessage != null -> {
                    Text(
                        text = "Error: ${state.errorMessage}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    Text(
                        text = "Temperatura: ${state.temperature ?: "-"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Viento: ${state.windSpeed ?: "-"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
