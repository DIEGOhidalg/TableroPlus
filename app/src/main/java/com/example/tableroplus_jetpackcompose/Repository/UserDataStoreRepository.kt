package com.example.tableroplus_jetpackcompose.Repository

// Importaciones necesarias en este archivo
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


data class DatosUsuario(
    val nombre: String,
    val correo: String,
    val clave: String,
    val imagenUri: String = "" // <-- Aquí está el nuevo dato
)

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserDataStoreRepository(private val context: Context) {

    // 1. Definimos las "llaves" para cada dato que queremos guardar
    private object PreferencesKeys {
        val NOMBRE = stringPreferencesKey("user_nombre")
        val CORREO = stringPreferencesKey("user_correo")
        val CLAVE = stringPreferencesKey("user_clave")
        val IMAGEN_URI = stringPreferencesKey("user_image_uri")
    }

    // 2. Función para GUARDAR los datos (suspendida, se llama desde una coroutine)

    suspend fun saveUserData(nombre: String, correo: String, clave: String, imagenUri: String) { // <-- AÑADIR
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOMBRE] = nombre
            preferences[PreferencesKeys.CORREO] = correo
            preferences[PreferencesKeys.CLAVE] = clave
            preferences[PreferencesKeys.IMAGEN_URI] = imagenUri // <-- AÑADIR
        }
    }

    // 3. Flow para LEER los datos (opcional, pero útil)
    // Esto nos permite observar cambios en los datos guardados
    val userDataFlow: Flow<DatosUsuario> = context.dataStore.data
        .map { preferences ->
            val nombre = preferences[PreferencesKeys.NOMBRE] ?: "" // Si no existe, devuelve ""
            val correo = preferences[PreferencesKeys.CORREO] ?: "" // Si no existe, devuelve
            val clave = preferences[PreferencesKeys.CLAVE] ?: ""
            val imagenUri = preferences[PreferencesKeys.IMAGEN_URI] ?: ""

            DatosUsuario(nombre, correo, clave, imagenUri)
        }
}