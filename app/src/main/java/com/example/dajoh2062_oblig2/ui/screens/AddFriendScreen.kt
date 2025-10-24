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
            },
            onCancel = { navController.popBackStack() }
        )
    }
}
