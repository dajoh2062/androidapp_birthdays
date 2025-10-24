package com.example.dajoh2062_oblig2.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dajoh2062_oblig2.R
import com.example.dajoh2062_oblig2.ui.components.SmsToggleRow
import com.example.dajoh2062_oblig2.ui.components.MessageInputField
import com.example.dajoh2062_oblig2.ui.viewmodel.PreferencesViewModel
import com.example.dajoh2062_oblig2.MyApp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dajoh2062_oblig2.BirthdayWorker

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

    val context = LocalContext.current
    val app = context.applicationContext as MyApp

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.preferences_title)) })
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
                onToggle = { isChecked ->
                    tempEnabled = isChecked

                    if (isChecked) {
                        // 🔹 Start daglig sjekk
                        app.scheduleDailyWork(context)

                        // 🔹 Kjør bursdagssjekk umiddelbart
                        val immediateWork = OneTimeWorkRequestBuilder<BirthdayWorker>().build()
                        WorkManager.getInstance(context).enqueue(immediateWork)

                        Toast.makeText(context, "Automatisk SMS aktivert og første sjekk startet", Toast.LENGTH_SHORT).show()
                    } else {
                        app.cancelDailyWork(context)
                        Toast.makeText(context, "Automatisk SMS deaktivert", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            Column {
                MessageInputField(
                    messageText = tempMessage,
                    onMessageChange = { tempMessage = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.message_placeholder_hint),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


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



