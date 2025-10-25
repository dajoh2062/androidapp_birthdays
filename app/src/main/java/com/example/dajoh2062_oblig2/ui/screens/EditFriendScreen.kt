package com.example.dajoh2062_oblig2.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.dajoh2062_oblig2.R
import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.ui.components.FriendForm
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel

/*
Skjermen åpnes fra "HomeScreen" når
brukeren trykker "Rediger" på et "FriendCard". Den henter først den
valgte personen fra "PersonViewModel" via "selectedPerson". Dersom ingen
person er valgt, vises en enkel tekstmelding. Ellers fylles
"FriendForm"-komponenten med eksisterende data, slik at brukeren kan
endre navn, telefon eller bursdag. Når skjemaet sendes inn, oppdateres
personen i databasen og skjermen navigerer tilbake til startsiden.
 */

// Må bruke @OptIn her også pga. topbar.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFriendScreen(
    navController: NavController,
    viewModel: PersonViewModel
) {
    val personToEdit: Person? = viewModel.selectedPerson

    // Dersom ingen person er valgt, vis en enkel tekstmelding. Skal ikek være mulig for
    // brukeren å komme til dette stadiet.
    if (personToEdit == null) {
        Text(text = stringResource(id = R.string.no_person_selected))
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.edit_friend_title)) }
            )
        }
    ) { padding ->
        FriendForm(
            modifier = Modifier.padding(paddingValues = padding),
            existingPerson = personToEdit, // fyller inn detaljene
            onSubmit = { updatedPerson ->
                viewModel.updatePerson(updatedPerson)
                viewModel.clearSelection()
                navController.popBackStack()
            },
            onCancel = {
                viewModel.clearSelection()
                navController.popBackStack()
                // tilbake til hjemskjermen uten å lage ett nytt instans over.
            }
        )
    }
}
