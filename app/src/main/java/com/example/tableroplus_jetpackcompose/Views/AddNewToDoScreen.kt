package com.example.tableroplus_jetpackcompose.Views

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tableroplus_jetpackcompose.Model.ToDo
import java.util.Calendar

@Composable
fun AddNewToDoScreen( navController: NavController, onSave: (ToDo)->Unit){
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Insertar Tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { datePickerDialog.show() }) {
            Text(text = if (date.isNotEmpty()) "Date: $date" else "Elegir Fecha")
        }

        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text("Es urgente?")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = isSwitchOn,
                onCheckedChange = { isSwitchOn = it }
            )
        }

        Button(
            onClick = {
                onSave(ToDo(name,date,isSwitchOn))
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Tarea")
        }
    }
}