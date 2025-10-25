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

/*
Denne klassen er appens hovedinngangspunkt og håndterer oppstart av
databasen, ViewModel og UI-et. Her settes Room-databasen opp via
"AppDatabase" og "PersonRepository", og en "PersonViewModel" opprettes
for å dele data mellom skjermene. Klassen sjekker også om brukeren har
gitt tillatelse til å sende SMS. Hvis ikke, vises en systemdialog som
ber om tillatelse. Når appen starter, sjekkes "SharedPreferences" for
om SMS-tjenesten tidligere ble aktivert. Til slutt initialiseres hele UI-et
gjennom "MyApp()", som definerer navigasjonen og skjermstrukturen.
 */
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

        // Oppretter databasen og repository for å koble til ViewModel
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "person_db"
        ).build()

        val repository = PersonRepository(db.personDao())
        val viewModel = PersonViewModel(repository, application)

        // Sjekker og ber eventuelt om SMS-tillatelse fra brukeren
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("SMS", "Tillatelse til å sende SMS")
        } else {
            beOmSmstillatelse.launch(Manifest.permission.SEND_SMS)
        }

        val prefs = getSharedPreferences("app_preferences", MODE_PRIVATE)
        if (prefs.getBoolean("sms_enabled", false)) {
            (application as MyApp).scheduleDailyWork(applicationContext)
            Log.d("Worker", "Bursdagssjekk aktivert automatisk ved oppstart")
        }
        // Starter Jetpack Compose-grensesnittet
        enableEdgeToEdge()
        setContent {
            Dajoh2062_oblig2Theme {
                MyApp(sharedViewModel = viewModel)
            }
        }
    }
}
