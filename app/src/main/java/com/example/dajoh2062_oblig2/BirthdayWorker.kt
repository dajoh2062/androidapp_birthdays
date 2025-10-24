package com.example.dajoh2062_oblig2

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.dajoh2062_oblig2.data.AppDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BirthdayWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("BirthdayWorker", "Starter bursdagssjekk...")

        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.personDao()
        val friends = dao.getAllPersonsDirect() // must return List<Person>

        val today = SimpleDateFormat("dd-MM", Locale.getDefault()).format(Date())

        val smsManager = SmsManager.getSmsManagerForSubscriptionId(
            SmsManager.getDefaultSmsSubscriptionId()
        )

        friends.forEach { friend ->
            if (friend.birthday.take(5) == today) {
                val message = "Gratulerer med dagen, ${friend.name}! 🎉"
                smsManager.sendTextMessage(friend.phone, null, message, null, null)
                Log.d("BirthdayWorker", "Sendte SMS til ${friend.name}")
            }
        }

        return Result.success()
    }
}