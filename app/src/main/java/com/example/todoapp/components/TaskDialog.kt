import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todoapp.screens.Autentification.AuthResponse
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TaskDialog(onDismiss: () -> Unit) {
    var taskName by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f) // ширина 90% экрана
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            shadowElevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Create a New Task", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(12.dp))

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Task Name") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.Gray,
                        disabledIndicatorColor = Color.LightGray,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = Color.Gray
                    ),
                    shape = RectangleShape // чтобы не было закруглений
                )


                Spacer(Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        dateDialogState.show()
                    }) {
                        Text("Set Date & Time")
                    }

                    Button(onClick = { /* Show category picker */ }) {
                        Text("Category")
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Показываем выбранную дату и время
                if (selectedDate != null && selectedTime != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    val dateTime = LocalDateTime.of(selectedDate, selectedTime)
                    val formatted = dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"))
                    Text("Selected: $formatted", fontSize = 14.sp)
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save")
                }
            }
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("OK") {
                timeDialogState.show()
            }
            negativeButton("Cancel")
        },
        shape = RoundedCornerShape(16.dp)
    ) {
        datepicker { date ->
            selectedDate = date
        }
    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("Cancel")
        },
        shape = RoundedCornerShape(16.dp)
    ) {
        timepicker { time ->
            selectedTime = time
        }
    }
}