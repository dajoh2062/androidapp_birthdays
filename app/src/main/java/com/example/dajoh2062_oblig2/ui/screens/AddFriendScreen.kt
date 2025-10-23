package com.example.dajoh2062_oblig2.ui.screens
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
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
            TopAppBar(title = { Text("Add Friend") })
        }
    ) { padding ->
        FriendForm(
            modifier = androidx.compose.ui.Modifier.padding(padding),
            existingPerson = null,
            onSubmit = { newPerson ->
                viewModel.addPerson(newPerson)
                navController.popBackStack()
            }
        )
    }
}
