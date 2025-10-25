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
/*
enne klassen definerer en bakgrunnsjobb som automatisk kjøres av
WorkManager for å sende bursdagshilsener via SMS. Jobben hentes fra
"PreferencesViewModel" og planlegges av "MyApp" enten som en daglig
rutine eller en umiddelbar engangskjøring. Implementeringen er lik den som ble vist
i canvas-powerpointen.
 */

class BirthdayWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("BirthdayWorker", "Starter bursdagssjekk...")


        // Henter database og DAO for å få tilgang til alle personer
        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.personDao()
        val friends = dao.getAllPersonsDirect()


        // Formatterer dagens dato som "dd-MM" for sammenligning
        val today = SimpleDateFormat("dd-MM", Locale.getDefault()).format(Date())

        // Leser lagret standardmelding fra SharedPreferences
        val prefs = applicationContext.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val defaultMessage = prefs.getString(
            "default_message",
            applicationContext.getString(R.string.default_message)
        ) ?: applicationContext.getString(R.string.default_message)

        val smsManager = SmsManager.getSmsManagerForSubscriptionId(
            SmsManager.getDefaultSmsSubscriptionId()
        )

        // alternativ smsManage som kan brukes istedenfor den over. Var usikker på om dette
        // nødvendig eller ikke, selvom det står en del av metoden over er utdatert.
        /*
        val smsManager = applicationContext
        .getSystemService(SmsManager::class.java)
        .createForSubscriptionId(subId)
         */

        var sent = 0
        friends.forEach { friend ->
            if (friend.birthday.take(5) == today) {
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
