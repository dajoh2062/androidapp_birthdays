package com.example.dajoh2062_oblig2.repositories

import com.example.dajoh2062_oblig2.data.Person

class PersonRepository {

    private val people = mutableListOf<Person>()

    // Henter alle personer
    fun getPeople(): List<Person> {
        return people
    }

    // Legger til en ny person
    fun addPerson(person: Person) {
        people.add(person)
    }

    // Fjerner en person
    fun removePerson(person: Person) {
        people.remove(person)
    }

    // Finner person basert på navn (valgfritt)
    fun findPerson(name: String): Person? {
        return people.find { it.name == name }
    }
}
