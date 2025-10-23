package com.example.dajoh2062_oblig2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.repositories.PersonRepository

class PersonViewModel : ViewModel() {
    private val personRepository = PersonRepository()

    private val _people = mutableStateListOf<Person>()
    val people: SnapshotStateList<Person> get() = _people

    var selectedPerson: Person? by mutableStateOf(null)
        private set

    fun selectPerson(person: Person) {
        selectedPerson = person
    }
    fun clearSelection() {
        selectedPerson = null
    }

    init {
        _people.addAll(personRepository.getPeople())
    }

    fun addPerson(person: Person) {
        personRepository.addPerson(person)
        _people.add(person)
    }

    fun removePerson(person: Person) {
        personRepository.removePerson(person)
        _people.remove(person)
    }

}
