package com.example.dajoh2062_oblig2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.dajoh2062_oblig2.data.AppDatabase
import com.example.dajoh2062_oblig2.repositories.PersonRepository
import com.example.dajoh2062_oblig2.ui.navigation.MyApp
import com.example.dajoh2062_oblig2.ui.theme.Dajoh2062_oblig2Theme
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel

// MainActivity.kt som start MyApp(), som ligger i NavGraph.kt.
// Der startes start-skjermbildet.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext, klass=AppDatabase::class.java, name="person_db"
        ).build()

        val repository = PersonRepository(dao=db.personDao())
        val viewModel= PersonViewModel(repository,application)

        enableEdgeToEdge()
        setContent {

            Dajoh2062_oblig2Theme {
                MyApp(sharedViewModel=viewModel)
            }
        }
    }
}
