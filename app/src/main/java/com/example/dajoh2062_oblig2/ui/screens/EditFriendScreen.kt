package com.example.dajoh2062_oblig2.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.ui.components.FriendForm
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFriendScreen(
    navController: NavController,
    viewModel: PersonViewModel
) {
    val personToEdit: Person? = viewModel.selectedPerson

    if (personToEdit == null) {
        // Fallback if user somehow opens Edit without a selected person
        Text("No person selected for editing.")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Friend") })
        }
    ) { padding ->
        FriendForm(
            modifier = androidx.compose.ui.Modifier.padding(padding),
            existingPerson = personToEdit,
            onSubmit = { updatedPerson ->
                viewModel.removePerson(personToEdit)
                viewModel.addPerson(updatedPerson)
                viewModel.clearSelection()
                navController.popBackStack()
            }
        )
    }
}
