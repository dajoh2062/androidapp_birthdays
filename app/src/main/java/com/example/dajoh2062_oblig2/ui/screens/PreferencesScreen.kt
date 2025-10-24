package com.example.dajoh2062_oblig2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dajoh2062_oblig2.R
import com.example.dajoh2062_oblig2.ui.components.SmsToggleRow
import com.example.dajoh2062_oblig2.ui.components.MessageInputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var smsEnabled by remember { mutableStateOf(value = false) }
    var messageText by remember { mutableStateOf(value = "Gratulerer med dagen!") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.preferences_title)) }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(paddingValues = padding)
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 20.dp)
        ) {
            SmsToggleRow(
                isChecked = smsEnabled,
                onToggle = { smsEnabled = it }
            )

            MessageInputField(
                messageText = messageText,
                onMessageChange = { messageText = it }
            )

            Button(
                onClick = {
                    // TODO: Save to DataStore / SharedPreferences later
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.save_preferences))
            }

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    }
}
