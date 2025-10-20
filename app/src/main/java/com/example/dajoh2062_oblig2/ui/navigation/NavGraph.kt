package com.example.dajoh2062_oblig2.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.ui.screens.EditFriendScreen
import com.example.dajoh2062_oblig2.ui.screens.AddFriendScreen
import com.example.dajoh2062_oblig2.ui.screens.PreferencesScreen
import com.example.dajoh2062_oblig2.ui.screens.HomeScreen

// Navigasjonsgraf for applikasjonen. Inneholder alle skjermbildene og rutene deres.
// Bruker NavHostController til å navigere mellom skjermbildene, som vist i canvas materialet.

@Composable
fun MyApp(){
    val navController = rememberNavController()
    NavigationGraph(navController = navController)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    // Første siden er start-skjermen.
    NavHost(navController = navController, startDestination = "home")
    {

        // Navigasjonsruter til de fire skjermene i prosjektet.
        composable("home") {
            HomeScreen(  navController = navController,
                friends = listOf(
                    Person(name = "Johan", phone = "12345678", birthDate = "12.03.2000"),
                    Person(name = "Maria", phone = "98765432", birthDate = "05.09.1999")
                ),
                onEdit = {},
                onDelete = {},
                onAddNew = {})
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