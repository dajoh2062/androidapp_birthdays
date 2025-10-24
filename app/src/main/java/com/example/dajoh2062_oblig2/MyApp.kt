package com.example.dajoh2062_oblig2


import android.app.Application
import android.content.Context
import androidx.work.*
import com.example.dajoh2062_oblig2.BirthdayWorker
import java.util.concurrent.TimeUnit

class MyApp : Application() {

    // Schedules the daily birthday check
    fun scheduleDailyWork(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<BirthdayWorker>(
            1, TimeUnit.DAYS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "Daglig bursdagssjekk",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    // Cancels the background work
    fun cancelDailyWork(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork("Daglig bursdagssjekk")
    }
}
