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
    NavigationGraph(navController = navController)
}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: PersonViewModel =viewModel()) {
    // Første siden er start-skjermen.
    NavHost(navController = navController, startDestination = "home")
    {
        // Navigasjonsruter til de fire skjermene i prosjektet.
        composable("home") {
            HomeScreen(  navController = navController, viewModel=viewModel,
                friends = listOf(
                    Person(name= "Johan", phone = "12345678", birthday = "12.03.2000"),
                    Person(name = "Maria", phone = "98765432", birthday = "05.09.1999")
                ),
                onEdit = {},
                onDelete = {},
                onAddNew = {},
                onSettings = {})
        }
        composable("preferences") {
            PreferencesScreen(navController = navController)
        }
        composable("edit") {
            EditFriendScreen(navController = navController)
        }
        composable("add") {
            AddFriendScreen(navController = navController)
        }
    }
}