package com.example.dajoh2062_oblig2

import android.app.Application
import android.content.Context
import androidx.work.*
import com.example.dajoh2062_oblig2.BirthdayWorker
import java.util.concurrent.TimeUnit

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

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(uniqueWorkName="Daglig bursdagssjekk",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun cancelDailyWork(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName="Daglig bursdagssjekk")
    }
}
