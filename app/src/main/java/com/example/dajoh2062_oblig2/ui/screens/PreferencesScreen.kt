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
import com.example.dajoh2062_oblig2.ui.viewmodel.PreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PreferencesViewModel
) {
    val smsEnabled by viewModel.smsEnabled.collectAsState()
    val messageText by viewModel.defaultMessage.collectAsState()

    var tempEnabled by remember { mutableStateOf(smsEnabled) }
    var tempMessage by remember { mutableStateOf(messageText) }

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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SmsToggleRow(
                isChecked = tempEnabled,
                onToggle = { tempEnabled = it }
            )

            MessageInputField(
                messageText = tempMessage,
                onMessageChange = { tempMessage = it }
            )

            Button(
                onClick = {
                    viewModel.setSmsEnabled(tempEnabled)
                    viewModel.setDefaultMessage(tempMessage)
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

