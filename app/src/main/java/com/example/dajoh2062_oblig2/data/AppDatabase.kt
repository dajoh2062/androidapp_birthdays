package com.example.dajoh2062_oblig2.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
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
