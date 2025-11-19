package com.example.tableroplus_jetpackcompose.Views

// ... (Todas tus importaciones están bien)
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import com.example.tableroplus_jetpackcompose.ViewModel.UsuarioViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.tableroplus_jetpackcompose.R
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import coil.compose.AsyncImage


@Composable
fun PerfilScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()
    var mostrarClave by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animación de escala (0.75f es mucho, 0.95f es más sutil, pero usa el que prefieras)
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        label = "scaleAnimation"
    )

    val miColorCyan = Color(0xFF48ECAB)
    val miColortexto = Color(0xFF368B91)
    val miColorCyanOscuro = Color(0xFF269D8C)
    val miColorCyanSinRegistro = Color(0xFF89BBB2)

    // --- ESTA ES LA ÚNICA COLUMNA PRINCIPAL ---
    Column(
        modifier = Modifier
            .fillMaxSize() // 1. Ocupa toda la pantalla
            .padding(horizontal = 14.dp), // 2. Añade padding a los lados
        verticalArrangement = Arrangement.Center, // 3. Centra todo el bloque verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // 4. Centra a todos los hijos horizontalmente
    ) {

        AsyncImage(
            // Si el URI está vacío, muestra el placeholder
            model = estado.imagenUri.ifEmpty { R.drawable.ic_profile_placeholder },
            contentDescription = "Imagen de Perfil",
            modifier = Modifier
                .size(120.dp) // Un tamaño más grande para el perfil
                .clip(CircleShape) // La hacemos redonda
                .background(Color.Gray.copy(alpha = 0.1f)) // Fondo por si la imagen tarda
                .border(2.dp, miColorCyanSinRegistro, CircleShape),

            contentScale = ContentScale.Crop // Asegura que la imagen llene el círculo
        )

        // --- Logo y Títulos ---

        Spacer(modifier = Modifier.height(24.dp)) // Espacio

        Text(
            text = "Mi Perfil",
            modifier = Modifier.padding(bottom = 14.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = miColorCyanOscuro,
        )

        Spacer(modifier = Modifier.height(17.dp)) // Espacio


        Text(
            text = "Nombre: ${estado.nombre}",
            modifier = Modifier.padding(bottom = 22.dp), // Espacio antes del formulario
            color = miColortexto,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Correo: ${estado.correo}",
            modifier = Modifier.padding(bottom = 22.dp), // Espacio antes del formulario
            color = miColortexto,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = ("Clave: ${estado.clave}"),
            modifier = Modifier.padding(bottom = 22.dp), // Espacio antes del formulario
            color = miColortexto,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleMedium
        )


        // --- Botones ---
        Button(
            onClick = {
                navController.navigate("EditarScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale), // Aplica la escala animada
            interactionSource = interactionSource,

            colors = ButtonDefaults.buttonColors(
                containerColor = miColorCyanOscuro, // El color de fondo del botón
                contentColor = Color.White    // El color del texto ("Registrar")
            )

        ) {
            Text( "Editar perfil")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio

        Text(
            text = "Volver",
            modifier = Modifier
                .clickable {
                    navController.navigate("ListOfTodos")
                },
            color = miColorCyanSinRegistro,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )


    }
}
