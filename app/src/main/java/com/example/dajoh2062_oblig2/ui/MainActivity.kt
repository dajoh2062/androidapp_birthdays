package com.example.dajoh2062_oblig2.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.dajoh2062_oblig2.data.AppDatabase
import com.example.dajoh2062_oblig2.repositories.PersonRepository
import com.example.dajoh2062_oblig2.ui.navigation.MyApp
import com.example.dajoh2062_oblig2.ui.theme.Dajoh2062_oblig2Theme
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModelFactory

// MainActivity.kt som start MyApp(), som ligger i NavGraph.kt.
// Der startes start-skjermbildet.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "person_db"
        ).build()

        val repository = PersonRepository(db.personDao())
        val factory = PersonViewModelFactory(repository, application)

        enableEdgeToEdge()
        setContent {
            val viewModel: PersonViewModel = viewModel(factory = factory)
            Dajoh2062_oblig2Theme {
                MyApp(sharedViewModel = viewModel)
            }
        }
    }
}
/*
    private val TAG = "MainActivity"


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

 */