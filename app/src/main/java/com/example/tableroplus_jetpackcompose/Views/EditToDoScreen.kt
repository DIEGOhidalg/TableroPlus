package com.example.tableroplus_jetpackcompose.Views

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Importa todo lo de layout (Box, Column, Row, etc)
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // Importa Button, Text, etc.
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
fun EditToDoScreen(
    navController: NavController,
    toDo: ToDo,
    index: Int,
    onSave: (Int, ToDo) -> Unit
) {
    // 1. Definimos el degradado (Brush)
    val brushNormal = Brush.linearGradient(
        colors = listOf(
            Color(0xFF26C6DA),
            Color(0xFF80DEEA),
            Color(0xFF50FFD6), // Repetimos para efecto brillo
        )
    )

    val miColorCyanOscuro = Color(0xFF269D8C)

    var name by remember { mutableStateOf(toDo.task) }
    var date by remember { mutableStateOf(toDo.date) }
    var isSwitchOn by remember { mutableStateOf(toDo.isUrgent) }

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
            .padding(24.dp), // Un poco más de margen se ve mejor
        // 2. Aquí está la magia para CENTRAR:
        verticalArrangement = Arrangement.Center, // Centra verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
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

        Text(
            text = "Editar Tarea",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF006064),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de la tarea") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- BOTÓN DE FECHA CON DEGRADADO ---
        Button(
            onClick = { datePickerDialog.show() },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(60.dp)) // Redondeado
                .background(brush = brushNormal), // Degradado
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Fondo transparente
                contentColor = Color.White
            ),
            contentPadding = PaddingValues() // Quitamos padding default
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
            horizontalArrangement = Arrangement.Center // Centramos el switch y el texto
        ) {
            Text("¿Es urgente?", fontSize = 18.sp, color = Color(0xFF006064),)
            Spacer(modifier = Modifier.width(12.dp))
            Switch(checked = isSwitchOn, onCheckedChange = { isSwitchOn = it }, colors = SwitchDefaults.colors(
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
                uncheckedBorderColor = Color.Transparent))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÓN GUARDAR CON DEGRADADO ---
        Button(
            onClick = {
                onSave(index, ToDo(name, date, isSwitchOn))
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(60.dp)) // Redondeado
                .background(brush = brushNormal), // Degradado
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Fondo transparente
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
                    text = "Guardar Cambios",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}