package com.example.dajoh2062_oblig2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.dajoh2062_oblig2.R

/*
Når brukeren slår bryteren på, trigges "onToggle", som i "PreferencesScreen" kaller funksjoner i
"PreferencesViewModel" for å oppdatere SharedPreferences og starte "WorkManager" som kjører
"BirthdayWorker" daglig. Når bryteren slås av, kanselleres den planlagte jobben. Denne raden
fungerer dermed som en direkte kobling mellom brukerens valg i grensesnittet og logikken som styrer
SMS-varslingene i bakgrunnen.
 */
@Composable
fun SmsToggleRow(
    isChecked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.enable_sms_service),
            style = MaterialTheme.typography.titleMedium
        )
        Switch(
            checked = isChecked,
            onCheckedChange = onToggle
        )
    }
}
