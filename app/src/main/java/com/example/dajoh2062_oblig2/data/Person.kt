package com.example.dajoh2062_oblig2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
Person klasse som brukes i databasen til å lagre venner. Med primarykey og de ulike
attributtene som vi trenger til venne-objektet. Veldig likt som en pojo i java.

 */

@Entity(tableName = "friends")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phone: String,
    val birthday: String
)