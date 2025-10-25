package com.example.dajoh2062_oblig2.repositories

import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.data.PersonDao
import kotlinx.coroutines.flow.Flow

/*
Igjen veldig likt repository ( + class + database + controller) som brukes i java.
Filen fungerer som en kommunikasjon mellom databasen/dao, og resten av applikasjonen,
veldig likt som ett java repository. Funksjonene her blir brukt ifra viewModel, for å bevare
den typise MVVM arkitekturen. Kan hente personer, legge til, endre eksisterende og slette.
Bruker DAO for å utføre funskjonene.
 */

class PersonRepository(private val dao: PersonDao) {

    // Flow gjør at alle personer oppdateres automatisk når data endres slik at appen
    // kan observre eendringer.
    val allFriends: Flow<List<Person>> = dao.getAllPersons()

    suspend fun addPerson(person: Person) {
        dao.insert(person)
    }

    suspend fun updatePerson(person: Person) {
        dao.update(person)
    }

    suspend fun removePerson(person: Person) {
        dao.delete(person)
    }
}
