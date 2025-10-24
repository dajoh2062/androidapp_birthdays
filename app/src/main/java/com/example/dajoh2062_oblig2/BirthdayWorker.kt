package com.example.dajoh2062_oblig2

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dajoh2062_oblig2.data.AppDatabase
import com.example.dajoh2062_oblig2.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BirthdayWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("BirthdayWorker", "Starter bursdagssjekk...")

        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.personDao()
        val friends = dao.getAllPersonsDirect()

        val today = SimpleDateFormat("dd-MM", Locale.getDefault()).format(Date())

        // Read default message set by user; fallback to strings.xml
        val prefs = applicationContext.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val defaultMessage = prefs.getString(
            "default_message",
            applicationContext.getString(R.string.default_message)
        ) ?: applicationContext.getString(R.string.default_message)

        val smsManager = SmsManager.getSmsManagerForSubscriptionId(
            SmsManager.getDefaultSmsSubscriptionId()
        )

        var sent = 0
        friends.forEach { friend ->
            if (friend.birthday.take(5) == today) {
                // Support both {navn} and {name} placeholders
                val message = defaultMessage
                    .replace("{navn}", friend.name)
                    .replace("{name}", friend.name)

                smsManager.sendTextMessage(friend.phone, null, message, null, null)
                Log.d("BirthdayWorker", "Sendte SMS til ${friend.name}")
                sent++
            }
        }

        Log.d("BirthdayWorker", "Fullført. Sendte $sent bursdagshilsener.")
        return Result.success()
    }
}
