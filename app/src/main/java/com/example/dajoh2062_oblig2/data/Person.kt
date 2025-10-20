package com.example.dajoh2062_oblig2.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phone: String,
    val birthDate: String
)