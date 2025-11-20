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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border


@Composable
fun RegistroScreen(
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

    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                // Esto es clave: Pedimos permiso permanente para leer este URI
                // para que la app pueda verlo incluso después de reiniciarse.
                try {
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    context.contentResolver.takePersistableUriPermission(uri, flag)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
                // Guardamos el URI (como String) en el ViewModel
                viewModel.onImagenUriChange(uri.toString())
            }
        }
    )


    // --- ESTA ES LA ÚNICA COLUMNA PRINCIPAL ---
    Column(
        modifier = Modifier
            .fillMaxSize() // 1. Ocupa toda la pantalla
            .padding(horizontal = 24.dp), // 2. Añade padding a los lados
        verticalArrangement = Arrangement.Center, // 3. Centra todo el bloque verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // 4. Centra a todos los hijos horizontalmente
    ) {

        // --- Logo y Títulos ---
        AsyncImage(
            // Si el URI está vacío, muestra el placeholder
            model = estado.imagenUri.ifEmpty { R.drawable.ic_profile_placeholder },
            contentDescription = "Imagen de Perfil",
            modifier = Modifier
                .size(120.dp) // Un tamaño más grande para el perfil
                .clip(CircleShape) // La hacemos redonda
                .background(Color.Gray.copy(alpha = 0.1f)) // Fondo por si la imagen tarda
                .border(2.dp, miColorCyanSinRegistro, CircleShape) // Borde
                .clickable {
                    // 3. AL HACER CLIC, LANZAMOS EL PICKER
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            contentScale = ContentScale.Crop // Asegura que la imagen llene el círculo
        )

        Spacer(modifier = Modifier.height(24.dp)) // Espacio

        Text(
            text = "Bienvenido a Tablero+",
            // No necesitas fillMaxWidth() si horizontalAlignment es Center
            modifier = Modifier.padding(bottom = 4.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Iniciar sesion para Tablero+",
            modifier = Modifier.padding(bottom = 32.dp), // Espacio antes del formulario
            color = Color.Gray,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall
        )

        // --- Campos de Formulario ---
        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Ingrese su nombre") },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                // Colores cuando SÍ está enfocado (escribiendo)
                focusedIndicatorColor = miColorCyanOscuro,         // Color del borde
                focusedLabelColor = miColorCyanOscuro,           // Color del label flotante
                cursorColor = miColorCyanOscuro,                 // Color del cursor parpadeante
                focusedTrailingIconColor = miColorCyanOscuro,  // Color del icono (ojito)

                // Colores cuando NO está enfocado (normal)
                unfocusedIndicatorColor = miColorCyanSinRegistro,        // Borde
                unfocusedLabelColor = miColorCyanSinRegistro,         // Label
                unfocusedTrailingIconColor = miColorCyanSinRegistro,     // Icono

                // Color del texto que el usuario escribe
                focusedTextColor = miColorCyanOscuro,
                unfocusedTextColor = miColorCyanOscuro,

                // Fondo (importante para OutlinedTextField)
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp)) // Espacio

        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,
            label = { Text("Ingrese su correo") },
            isError = estado.errores.correo != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                // Colores cuando SÍ está enfocado (escribiendo)
                focusedIndicatorColor = miColorCyanOscuro,         // Color del borde
                focusedLabelColor = miColorCyanOscuro,           // Color del label flotante
                cursorColor = miColorCyanOscuro,                 // Color del cursor parpadeante
                focusedTrailingIconColor = miColorCyanOscuro,  // Color del icono (ojito)

                // Colores cuando NO está enfocado (normal)
                unfocusedIndicatorColor = miColorCyanSinRegistro,        // Borde
                unfocusedLabelColor = miColorCyanSinRegistro,         // Label
                unfocusedTrailingIconColor = miColorCyanSinRegistro,     // Icono

                // Color del texto que el usuario escribe
                focusedTextColor = miColorCyanOscuro,
                unfocusedTextColor = miColorCyanOscuro,

                // Fondo (importante para OutlinedTextField)
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp)) // Espacio

        OutlinedTextField(

            value = estado.clave,
            onValueChange = viewModel::onClaveChange,
            label = { Text("Ingrese su clave (mínimo 6 caracteres)") },
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            visualTransformation = if (mostrarClave)
                VisualTransformation.None
            else
                PasswordVisualTransformation('*'),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val icono = if (mostrarClave) "👁" else "👁"
                IconButton(onClick = { mostrarClave = !mostrarClave }) {
                    Text(icono)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                // Colores cuando SÍ está enfocado (escribiendo)
                focusedIndicatorColor = miColorCyanOscuro,         // Color del borde
                focusedLabelColor = miColorCyanOscuro,           // Color del label flotante
                cursorColor = miColorCyanOscuro,                 // Color del cursor parpadeante
                focusedTrailingIconColor = miColorCyanOscuro,  // Color del icono (ojito)

                // Colores cuando NO está enfocado (normal)
                unfocusedIndicatorColor = miColorCyanSinRegistro,        // Borde
                unfocusedLabelColor = miColorCyanSinRegistro,         // Label
                unfocusedTrailingIconColor = miColorCyanSinRegistro,     // Icono

                // Color del texto que el usuario escribe
                focusedTextColor = miColorCyanOscuro,
                unfocusedTextColor = miColorCyanOscuro,

                // Fondo (importante para OutlinedTextField)
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(24.dp)) // Espacio

        // --- Botones ---
        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    navController.navigate("ListOfTodos")
                }

                viewModel.guardarUsuario()

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
            Text( "Registrar")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio

        Text(
            text = "Continuar sin registro",
            modifier = Modifier
                .clickable {
                    navController.navigate("ListOfTodos")
                    viewModel.onNombreChange("Invitado")
                },
            color = miColorCyanSinRegistro,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )


    }
}




