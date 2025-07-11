
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.todoapp.components.dialog.CategoryDialog
import com.example.todoapp.components.dialog.TaskDialogContent
import com.example.todoapp.components.dialog.TaskDialogState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState



@Composable
fun TaskDialog(
    onDismiss: () -> Unit,
    state: TaskDialogState = remember { TaskDialogState() }
) {
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    var showCategoryDialog by remember { mutableStateOf(false) }


    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            shadowElevation = 8.dp
        ) {
            TaskDialogContent(
                state = state,
                onDismiss = onDismiss,
                onShowDateDialog = { dateDialogState.show() },
                onShowCategoryDialog = { showCategoryDialog = true }
            )
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("OK") { timeDialogState.show() }
            negativeButton("Cancel")
        },
        shape = RoundedCornerShape(16.dp)
    ) {
        datepicker { state.selectedDate = it }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("Cancel")
        },
        shape = RoundedCornerShape(16.dp)
    ) {
        timepicker { state.selectedTime = it }
    }

    if (showCategoryDialog) {
        CategoryDialog(
            onDismiss = { showCategoryDialog = false },
            onCategorySelected = {
                state.selectedCategory = it
                showCategoryDialog = false
            }
        )
    }
}