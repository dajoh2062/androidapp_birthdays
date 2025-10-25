package com.example.dajoh2062_oblig2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
DAO er den direkte koblingen til databasen. PersonDao inneholder metodene som lar appen kommunisere
med databasen, for å sette inn,oppdatere, slette og hente personer. Ved å bruke Room-annotasjoner
(@Insert, @Update, @Delete, @Query)oversetter Android automatisk disse metodene til SQL-spørringer.
getAllPersons() returnerer en Flow slik at endringer i databasen kan observeres i sanntid.
 */
@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: Person)

    @Update
    suspend fun update(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Query(value="SELECT * FROM friends ORDER BY id DESC")
    fun getAllPersons(): Flow<List<Person>>

    @Query(value="SELECT * FROM friends")
    fun getAllPersonsDirect(): List<Person>

}
