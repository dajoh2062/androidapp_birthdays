package com.example.dajoh2062_oblig2.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

/*
Room-databasen for appen, som vises med taggen @Database, og ":RoomDatabase()".
@Database-annotasjonen genererer all nødvendig databasekode automatisk.
getInstance()-funksjonen sørger for at databasen kun opprettes en gang.
 */

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao


    companion object {// Statisk del av klassen (deles av alle instanser)
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            //Returnerer eksisterende instans eller oppretter en ny hvis den ikke finnes
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "person_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
