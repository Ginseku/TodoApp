package com.example.todoapp.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DateTimeSection(
    selectedDate: LocalDate?,
    selectedTime: LocalTime?,
    onSetDateClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
    }

    Spacer(Modifier.height(8.dp))

    if (selectedDate != null && selectedTime != null) {
        val dateTime = LocalDateTime.of(selectedDate, selectedTime)
        val formatted = dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"))
        Text("Selected: $formatted", fontSize = 14.sp)
    }
}
