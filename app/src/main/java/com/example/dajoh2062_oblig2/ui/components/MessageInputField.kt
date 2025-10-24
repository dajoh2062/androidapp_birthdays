package com.example.dajoh2062_oblig2.ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.dajoh2062_oblig2.R

@Composable
fun MessageInputField(
    messageText: String,
    onMessageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = messageText,
        onValueChange = onMessageChange,
        label = { Text(text = stringResource(id = R.string.default_message_label)) },
        modifier = modifier
    )
}
