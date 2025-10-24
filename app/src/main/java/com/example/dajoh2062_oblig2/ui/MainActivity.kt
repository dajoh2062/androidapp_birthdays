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
import com.example.dajoh2062_oblig2.MyApp
import com.example.dajoh2062_oblig2.data.AppDatabase
import com.example.dajoh2062_oblig2.repositories.PersonRepository
import com.example.dajoh2062_oblig2.ui.navigation.MyApp
import com.example.dajoh2062_oblig2.ui.theme.Dajoh2062_oblig2Theme
import com.example.dajoh2062_oblig2.ui.viewmodel.PersonViewModel

class MainActivity : ComponentActivity() {
    private val beOmSmstillatelse = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("SMS", "Tillatelse til å sende SMS")
        } else {
            Log.d("SMS", "Ikke tillatelse til å sende SMS")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "person_db"
        ).build()

        val repository = PersonRepository(db.personDao())
        val viewModel = PersonViewModel(repository, application)

        // 📱 Check SMS permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("SMS", "Tillatelse til å sende SMS")
        } else {
            beOmSmstillatelse.launch(Manifest.permission.SEND_SMS)
        }

        // ✅ Automatically start background birthday checks if user enabled SMS previously
        val prefs = getSharedPreferences("app_preferences", MODE_PRIVATE)
        if (prefs.getBoolean("sms_enabled", false)) {
            (application as MyApp).scheduleDailyWork(applicationContext)
            Log.d("Worker", "Bursdagssjekk aktivert automatisk ved oppstart")
        }

        // 🧱 UI
        enableEdgeToEdge()
        setContent {
            Dajoh2062_oblig2Theme {
                MyApp(sharedViewModel = viewModel)
            }
        }
    }
}
