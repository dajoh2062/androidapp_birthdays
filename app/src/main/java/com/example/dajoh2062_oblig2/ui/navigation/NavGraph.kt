package com.example.dajoh2062_oblig2.ui.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.ui.screens.EditFriendScreen
import com.example.dajoh2062_oblig2.ui.screens.AddFriendScreen
import com.example.dajoh2062_oblig2.ui.screens.PreferencesScreen
import com.example.dajoh2062_oblig2.ui.screens.HomeScreen
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel

// Navigasjonsgraf for applikasjonen. Inneholder alle skjermbildene og rutene deres.
// Bruker NavHostController til å navigere mellom skjermbildene, som vist i canvas materialet.

@Composable
fun MyApp(){
    val navController = rememberNavController()
    val sharedViewModel: PersonViewModel = viewModel()
    NavigationGraph(navController = navController, sharedViewModel = sharedViewModel)
}

@Composable
fun NavigationGraph(navController: NavHostController, sharedViewModel: PersonViewModel) {
    // Første siden er start-skjermen.
    NavHost(navController = navController, startDestination = "home")
    {
        // Navigasjonsruter til de fire skjermene i prosjektet.
        composable("home") {
            HomeScreen(
                navController = navController,
                viewModel = sharedViewModel,
                onEdit = { person ->
                    sharedViewModel.selectPerson(person)   // <--- SET selectedPerson first
                    navController.navigate("edit")         // <--- THEN navigate
                },
                onDelete = { person -> sharedViewModel.removePerson(person) },
                onAddNew = { navController.navigate("add") },
                onSettings = { navController.navigate("preferences") }
            )
        }

        composable("preferences") {
            PreferencesScreen(navController = navController)
        }
        composable("edit") {
            EditFriendScreen(
                navController = navController,
                viewModel = sharedViewModel,
            )
        }

        composable("add") {
            AddFriendScreen(
                navController = navController,
                viewModel = sharedViewModel
            )
        }
    }
}