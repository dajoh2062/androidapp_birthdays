package com.example.dajoh2062_oblig2.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.dajoh2062_oblig2.R

/*
Kunne delt denne i flere composables, men så ikke poenget i det. Dette er en composable
for å få en bekreftelse av brukeren, før eventuell sletting av venn fra listen. Bruker stringresources
som i alle andre tilfeller, og onCorfirm/onCancel som parameter for å separere funksjonalitet fra
design.
 */

@Composable
fun ConfirmDeletionDialog(
    friendName: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        //Lukkes også hvis brukeren trykker utenfor dialogen
        onDismissRequest = onCancel,
        title = { Text(text = stringResource(id = R.string.confirm_deletion)) },
        text = {
            Text(
                text = stringResource(
                    id = R.string.are_you_sure_delete,
                    formatArgs = arrayOf(friendName)
                )
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}
