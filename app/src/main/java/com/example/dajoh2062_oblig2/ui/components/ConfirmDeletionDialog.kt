package com.example.dajoh2062_oblig2.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ConfirmDeletionDialog(
    friendName: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text="Confirm Deletion") },
        text = { Text(text="Are you sure you want to delete $friendName?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text="Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text="Cancel")
            }
        }
    )
}
