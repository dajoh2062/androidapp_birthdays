package com.example.dajoh2062_oblig2.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.repositories.PersonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonViewModel(
    private val repository: PersonRepository,
    application: Application
) : AndroidViewModel(application) {

    val people: StateFlow<List<Person>> =
        repository.allFriends.stateIn(
            viewModelScope, started=SharingStarted.Lazily, initialValue=emptyList()
        )

    var selectedPerson: Person? by mutableStateOf(value=null)
        private set

    fun selectPerson(person: Person) {
        selectedPerson = person
    }

    fun clearSelection() {
        selectedPerson = null
    }

    fun addPerson(person: Person) {
        viewModelScope.launch {
            repository.addPerson(person)
        }
    }

    fun updatePerson(person: Person) {
        viewModelScope.launch {
            repository.updatePerson(person)
        }
    }

    fun removePerson(person: Person) {
        viewModelScope.launch {
            repository.removePerson(person)
        }
    }
}
