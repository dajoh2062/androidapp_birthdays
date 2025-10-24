package com.example.dajoh2062_oblig2

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dajoh2062_oblig2.data.AppDatabase
import java.text.SimpleDateFormat
import java.util.*

class BirthdayWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("BirthdayWorker", "Starter bursdagssjekk...")

        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.personDao()
        val friends = dao.getAllPersonsDirect() // Direkte liste fra DAO

        val today = SimpleDateFormat("dd-MM", Locale.getDefault()).format(Date())

        val prefs = applicationContext.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val defaultMessage = prefs.getString("default_message", "Gratulerer med dagen!") ?: "Gratulerer med dagen!"

        val smsManager = SmsManager.getSmsManagerForSubscriptionId(
            SmsManager.getDefaultSmsSubscriptionId()
        )

        var sentCount = 0

        friends.forEach { friend ->
            if (friend.birthday.take(5) == today) {
                val message = defaultMessage.replace("{navn}", friend.name)
                smsManager.sendTextMessage(friend.phone, null, message, null, null)
                Log.d("BirthdayWorker", "Sendte SMS til ${friend.name}")
                sentCount++
            }
        }

        Log.d("BirthdayWorker", "Fullført. Sendte $sentCount bursdagshilsener.")
        return Result.success()
    }
}
