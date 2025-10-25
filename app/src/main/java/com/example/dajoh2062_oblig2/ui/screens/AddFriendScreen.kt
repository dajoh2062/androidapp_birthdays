package com.example.dajoh2062_oblig2.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.dajoh2062_oblig2.R
import com.example.dajoh2062_oblig2.ui.components.FriendForm
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel
/*
 Skjermen bruker "FriendForm"-komponenten til å hente inn
informasjon som navn, telefonnummer og bursdag. Når brukeren trykker
"Legg til", kalles "onSubmit", som sender dataen videre til
"PersonViewModel" der vennen lagres i Room-databasen. Etterpå går
navigasjonen automatisk tilbake til hjemmeskjermen.
 */

// Må bruke @OptIn tydeligvis siden topbar var eksperimentelt.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendScreen(
    navController: NavController,
    viewModel: PersonViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.add_friend_title)) }
            )
        }
    ) { padding ->
        FriendForm(
            modifier = Modifier.padding(paddingValues=padding),
            existingPerson = null,
            onSubmit = { newPerson ->
                viewModel.addPerson(newPerson)
                navController.popBackStack()
                // navigerer tilbake til "HomeScreen" som eksisterer allerede, istedenfor
                       // å for eksempel navigere til ett nytt instans av skjermen.
            },
            onCancel = { navController.popBackStack() }
        )
    }
}
