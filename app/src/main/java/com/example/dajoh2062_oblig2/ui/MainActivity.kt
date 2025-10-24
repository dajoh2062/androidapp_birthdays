package com.example.dajoh2062_oblig2.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.dajoh2062_oblig2.data.AppDatabase
import com.example.dajoh2062_oblig2.repositories.PersonRepository
import com.example.dajoh2062_oblig2.ui.navigation.MyApp
import com.example.dajoh2062_oblig2.ui.theme.Dajoh2062_oblig2Theme
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel

// MainActivity.kt som start MyApp(), som ligger i NavGraph.kt.
// Der startes start-skjermbildet.
class MainActivity : ComponentActivity() {
    private val beomSmstillatelse = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    )
    { isGranted: Boolean ->
        if (isGranted) {
            Log.d("SMS", "Tillatelse til å sende SMS")
        } else {
            Log.d("SMS", "Ikke tillatelse til å sende SMS")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext, klass=AppDatabase::class.java, name="person_db"
        ).build()


        val repository = PersonRepository(dao=db.personDao())
        val viewModel= PersonViewModel(repository,application)

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("SMS","Tillatelse til å sende SMS")
        } else
        {
            beomSmstillatelse.launch(Manifest.permission.SEND_SMS)
        }


        enableEdgeToEdge()
        setContent {

            Dajoh2062_oblig2Theme {
                MyApp(sharedViewModel=viewModel)
            }
        }
    }
}
