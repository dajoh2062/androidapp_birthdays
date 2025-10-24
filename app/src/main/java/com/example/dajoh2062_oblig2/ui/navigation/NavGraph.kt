package com.example.dajoh2062_oblig2.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dajoh2062_oblig2.ui.screens.EditFriendScreen
import com.example.dajoh2062_oblig2.ui.screens.AddFriendScreen
import com.example.dajoh2062_oblig2.ui.screens.PreferencesScreen
import com.example.dajoh2062_oblig2.ui.screens.HomeScreen
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel

// Navigasjonsgraf for applikasjonen. Inneholder alle skjermbildene og rutene deres.
// Bruker NavHostController til å navigere mellom skjermbildene, som vist i canvas materialet.

@Composable
fun MyApp(sharedViewModel: PersonViewModel) {
    val navController = rememberNavController()
    NavigationGraph(navController = navController, sharedViewModel = sharedViewModel)
}

@Composable
fun NavigationGraph(navController: NavHostController, sharedViewModel: PersonViewModel) {
    // Første siden er start-skjermen.
    NavHost(navController = navController, startDestination = "home")
    {
        // Navigasjonsruter til de fire skjermene i prosjektet.
        composable(route="home") {
            HomeScreen(
                navController = navController,
                viewModel = sharedViewModel,
                onEdit = { person ->
                    sharedViewModel.selectPerson(person)
                    navController.navigate(route="edit")
                },
                onAddNew = { navController.navigate(route="add") },
                onSettings = { navController.navigate(route="preferences") }
            )
        }

        composable(route="preferences") {
            PreferencesScreen(navController = navController)
        }

        composable(route="edit") {
            EditFriendScreen(
                navController = navController,
                viewModel = sharedViewModel,
            )
        }

        composable(route="add") {
            AddFriendScreen(
                navController = navController,
                viewModel = sharedViewModel
            )
        }
    }
}