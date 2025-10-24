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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFriendScreen(
    navController: NavController,
    viewModel: PersonViewModel
) {
    val personToEdit: Person? = viewModel.selectedPerson

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
            existingPerson = personToEdit,
            onSubmit = { updatedPerson ->
                viewModel.updatePerson(updatedPerson)
                viewModel.clearSelection()
                navController.popBackStack()
            },
            onCancel = {
                viewModel.clearSelection()
                navController.popBackStack()
            }
        )
    }
}
