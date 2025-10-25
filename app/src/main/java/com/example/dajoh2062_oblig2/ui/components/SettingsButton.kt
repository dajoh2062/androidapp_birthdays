package com.example.dajoh2062_oblig2.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dajoh2062_oblig2.R

/*
Denne komponenten er en del av brukergrensesnittet på hjemmeskjermen (HomeScreen) og representerer
innstillingsknappen i nederste venstre hjørne. Når brukeren trykker på denne knappen, trigges
"onClick" som sendes fra HomeScreen, og navigasjonen styres videre via "NavController" til "preferences".
Her kan SMS-varsling kan aktiveres, deaktiveres eller meldingen endres.
*/
@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Likt design/formatering som add button.
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(size = 16.dp),
        modifier = modifier.size(size = 64.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(id = R.string.settings),
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(size = 28.dp)
        )
    }
}
