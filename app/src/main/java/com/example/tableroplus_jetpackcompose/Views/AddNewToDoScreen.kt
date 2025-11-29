package com.example.tableroplus_jetpackcompose.Views

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Importa todo (Box, Column, Row, etc)
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // Importa Button, Text, etc
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tableroplus_jetpackcompose.Model.ToDo
import java.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.tableroplus_jetpackcompose.R

@Composable
fun AddNewToDoScreen(
    navController: NavController,
    onSave: (ToDo) -> Unit
) {
    // 1. Definimos el mismo degradado Cyan
    val brushNormal = Brush.linearGradient(
        colors = listOf(
            Color(0xFF26C6DA),
            Color(0xFF80DEEA),
            Color(0xFF50FFD6),
        )
    )

    val miColorCyanOscuro = Color(0xFF269D8C)

    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var isSwitchOn by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // --- CONTENEDOR PRINCIPAL CENTRADO ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        // 2. Centramos todo el contenido
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tablero_plus_logo),
            contentDescription = "Logo App",
            modifier = Modifier
                .size(100.dp) // Ajusta el tamaño que prefieras (ej. 80.dp o 120.dp)
            // Opcional: si tu logo es cuadrado y quieres redondearlo como los botones:
            // .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre logo y título

        // Título decorativo
        Text(
            text = "Nueva Tarea",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF006064),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Insertar Tarea") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp) // Bordes redondeados
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- BOTÓN DE FECHA CON DEGRADADO ---
        Button(
            onClick = { datePickerDialog.show() },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(60.dp))
                .background(brush = brushNormal),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (date.isNotEmpty()) "Fecha: $date" else "Elegir Fecha",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center // Centramos el switch
        ) {
            Text("¿Es urgente?", fontSize = 18.sp,  color = Color(0xFF006064),)

            Spacer(modifier = Modifier.width(12.dp), )
            Switch(
                checked = isSwitchOn,
                onCheckedChange = { isSwitchOn = it },
                colors = SwitchDefaults.colors(
                    // --- ESTADO ACTIVO (ON) ---
                    // El círculo cuando está prendido (Blanco se ve limpio)
                    checkedThumbColor = Color.White,
                    // La barra de fondo cuando está prendido (Tu Cyan)
                    checkedTrackColor = Color(0xFF26C6DA),
                    // El borde (lo hacemos transparente para que se vea moderno)
                    checkedBorderColor = Color.Transparent,

                    // --- ESTADO INACTIVO (OFF) ---
                    // El círculo cuando está apagado
                    uncheckedThumbColor = Color.White,
                    // La barra de fondo cuando está apagado (Gris suave)
                    uncheckedTrackColor = Color(0xFFE0E0E0),
                    uncheckedBorderColor = Color.Transparent)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÓN GUARDAR CON DEGRADADO ---
        Button(
            onClick = {
                // Validamos que no esté vacío el nombre para evitar tareas en blanco
                if (name.isNotEmpty()) {
                    onSave(ToDo(name, date, isSwitchOn))
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(60.dp))
                .background(brush = brushNormal),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Guardar Tarea",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}