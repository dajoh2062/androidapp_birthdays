package com.example.dajoh2062_oblig2

import android.app.Application
import android.content.Context
import androidx.work.*
import com.example.dajoh2062_oblig2.BirthdayWorker
import java.util.concurrent.TimeUnit

/*
Gir appen et globalt startpunkt der logikk som skal gjelde hele applikasjonen
kan ligge. Den opprettes automatisk når appen starter, før noen skjermer
eller aktiviteter lastes. Her brukes den som et sentralt sted for å
styre bakgrunnsjobben som sender bursdagshilsener. I stedet for å
konfigurere WorkManager flere steder i koden, samles all planlegging
og kansellering her.
 */

class MyApp : Application() {

    fun scheduleDailyWork(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<BirthdayWorker>(repeatInterval=1, repeatIntervalTimeUnit=TimeUnit.DAYS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        // Bare en unik jobb er aktiv om gangen
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(uniqueWorkName="Daglig bursdagssjekk",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
    // Stopper planlagt daglig bursdagssjekk
    fun cancelDailyWork(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName="Daglig bursdagssjekk")
    }
}
