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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dajoh2062_oblig2.BirthdayWorker
import com.example.dajoh2062_oblig2.R
import com.example.dajoh2062_oblig2.ui.components.SmsToggleRow
import com.example.dajoh2062_oblig2.ui.components.MessageInputField
import com.example.dajoh2062_oblig2.ui.viewmodel.PreferencesViewModel
import com.example.dajoh2062_oblig2.MyApp

/*
Her kan man slå på eller av den automatiske SMS-tjenesten og skrive
inn en standardmelding som brukes av "BirthdayWorker" når en venn har
bursdag. Når bryteren slås på, startes "WorkManager" via "MyApp" for å
planlegge en daglig jobb. I tillegg trigges en umiddelbar én-gangs
"BirthdayWorker" slik at bursdager sjekkes med en gang. Når bryteren
slås av, kanselleres den planlagte jobben. Endringene lagres i
"SharedPreferences" gjennom "PreferencesViewModel", som gjør at
innstillingene huskes etter appen lukkes. Skjermen brukes som en
egen rute i navigasjonen og nås via innstillingsknappen på "HomeScreen".

 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PreferencesViewModel
) {
    // Observerer lagrede verdier fra SharedPreferences via ViewModel
    val smsEnabled by viewModel.smsEnabled.collectAsState()
    val messageText by viewModel.defaultMessage.collectAsState()

    // Midlertidige variabler for brukerens endringer i UI før lagring
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
                        // Når SMS-tjenesten aktiveres:
                        // planlegg daglig arbeid og kjør en umiddelbar sjekk
                        app.scheduleDailyWork(context)

                        val immediateWork = OneTimeWorkRequestBuilder<BirthdayWorker>().build()
                        WorkManager.getInstance(context).enqueue(immediateWork)

                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_sms_enabled),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Når SMS-tjenesten deaktiveres:
                        // stopp planlagt arbeid
                        app.cancelDailyWork(context)
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_sms_disabled),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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
