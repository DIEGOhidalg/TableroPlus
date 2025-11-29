package com.example.tableroplus_jetpackcompose.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight


//  Degradado para tareas NORMALES
val brushNormal = Brush.linearGradient(
    colors = listOf(
        // Cyan claro
        Color(0xFF26C6DA),
        Color(0xFF80DEEA),
        Color(0xFF50FFD6),
    )
)

// 2. Degradado para tareas URGENTES
val brushUrgente = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFAB40),
        Color(0xFFFFCC80),
        Color(0xFFECFF70),
    )
)

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
            // 1. Creamos una Box para contener el fondo degradado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = brushNormal) // Usamos tu degradado Cyan (o crea uno nuevo)
            ) {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                "Tablero Plus+",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                " Bienvenido ${estado.nombre}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    },
                    // 2. Aquí está la magia: Hacemos el TopAppBar transparente
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent, // ¡IMPORTANTE!
                        titleContentColor = Color.White,    // Texto blanco
                        actionIconContentColor = Color.White // Iconos blancos
                    ),
                    actions = {
                        IconButton(onClick = {
                            navController.navigate("PerfilScreen")
                        }) {
                            // Validamos si hay imagen
                            if (estado.imagenUri.isNotEmpty()) {
                                AsyncImage(
                                    model = estado.imagenUri,
                                    contentDescription = "Foto",
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .border(1.dp, Color.White, CircleShape), // Borde blanco queda genial
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Perfil"
                                )
                            }
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            Box( // Usamos Box para poder ponerle el degradado al fondo
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp)) // Cuadrado redondeado
                    .background(brush = brushNormal) // Usamos el mismo degradado Cyan
                    .clickable {
                        navController.navigate("AddNewTodo")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp), // Un poco de margen a los lados se ve mejor
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre tareas
        ) {

            // HEADER DE CLIMA
            item {
                WeatherHeader(weatherUiState)
            }
            
            items(count = list.toList().count()) { index ->
                
                val tarea = list.toList()[index]

                // Elegimos el pincel según la urgencia
                val fondoActual = if (tarea.isUrgent) brushUrgente else brushNormal

                // --- TARJETA DE TAREA MEJORADA ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)) // Redondeamos las esquinas
                        .background(brush = fondoActual) // <--- AQUÍ APLICAMOS EL DEGRADADO
                        .padding(16.dp), // Padding interno para que el texto no toque el borde
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Columna de textos
                    Column(
                        modifier = Modifier.weight(1f) // Ocupa todo el espacio disponible
                    ) {
                        Text(
                            text = tarea.task,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White // Texto blanco para contraste
                        )
                        Text(
                            text = tarea.date,
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f) // Blanco un poco transparente
                        )
                    }

                    // --- ACCIONES (Switch, Editar, Borrar) ---
                    // Para que se parezca a la imagen derecha (más limpio),
                    // podrías ocultar estos botones o hacerlos blancos.
                    // Por ahora, los dejaremos pero en blanco.

                    Switch(
                        checked = tarea.isUrgent,
                        onCheckedChange = { onswitch(index, it) },
                        colors = SwitchDefaults.colors(
                                // --- ESTADO ACTIVO (ON) ---
                                // El círculo cuando está prendido (Blanco se ve limpio)
                                checkedThumbColor = Color.White,
                                // La barra de fondo cuando está prendido (Tu Cyan)
                                checkedTrackColor = Color(0xFFDA9826),
                                // El borde (lo hacemos transparente para que se vea moderno)
                                checkedBorderColor = Color.Transparent,

                                // --- ESTADO INACTIVO (OFF) ---
                                // El círculo cuando está apagado
                                uncheckedThumbColor = Color.White,
                                // La barra de fondo cuando está apagado (Gris suave)
                                uncheckedTrackColor = Color(0xFFE0E0E0),
                                uncheckedBorderColor = Color.Transparent
                        )
                    )

                    IconButton(onClick = { navController.navigate("EditTodo/$index") }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                    }

                    IconButton(onClick = { ondelete(tarea) }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
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
