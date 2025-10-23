package com.example.dajoh2062_oblig2.repositories

import com.example.dajoh2062_oblig2.data.Person

class PersonRepository {

    private val people = mutableListOf(
        Person(name = "Josef", phone = "12345678", birthday = "12.03.2000"),
        Person(name = "Maria", phone = "98765432", birthday = "05.09.1999")
    )

    fun getPeople(): List<Person> {
        return people
    }

    fun addPerson(person: Person) {
        people.add(person)
    }

    fun removePerson(person: Person) {
        people.remove(person)
    }

    fun findPerson(name: String): Person? {
        return people.find { it.name == name }
    }
}
