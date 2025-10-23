package com.example.dajoh2062_oblig2.repositories

import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.data.PersonDao
import kotlinx.coroutines.flow.Flow

class PersonRepository(private val dao: PersonDao) {

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
