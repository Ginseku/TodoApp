package com.example.todoapp.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalTime

// --- UI State holder ---
data class TaskDialogState(
    var taskName: String = "",
    var selectedDate: LocalDate? = null,
    var selectedTime: LocalTime? = null,
    var selectedCategory: String? = null
)

// --- Dialog Content UI ---
@Composable
fun TaskDialogContent(
    state: TaskDialogState,
    onDismiss: () -> Unit,
    onShowDateDialog: () -> Unit,
    onShowCategoryDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Create a New Task", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(12.dp))

        TaskNameField(value = state.taskName, onChange = { state.taskName = it })

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = onShowDateDialog) { Text("Set Date") }
            Button(onClick = onShowCategoryDialog) { Text("Category") }
        }

        Spacer(Modifier.height(12.dp))

        DateTimeSection(
            selectedDate = state.selectedDate,
            selectedTime = state.selectedTime,
            onSetDateClick = onShowDateDialog
        )

        Spacer(Modifier.height(4.dp))

        state.selectedCategory?.let {
            Text("Category: $it", fontSize = 14.sp)
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = onDismiss, modifier = Modifier.align(Alignment.End)) {
            Text("Save")
        }
    }
}