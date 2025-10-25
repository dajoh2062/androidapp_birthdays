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

/*
Denne ViewModel-en fungerer som bindeleddet mellom UI
og databasen for vennelisten. Den henter, oppdaterer og fjerner venner
ved å kommunisere med "PersonRepository", som igjen bruker Room via
"PersonDao". Alle endringer skjer i en coroutine via "viewModelScope"
slik at operasjonene kjører asynkront og ikke blokkerer tråden.
"people" eksponeres som en "StateFlow", slik at skjermene automatisk
oppdateres når databasen endres. I tillegg håndteres valg av venn ved
redigering gjennom "selectedPerson", slik at "EditFriendScreen" vet
hvilken person som skal forhåndsutfylles i skjemaet.
*/

class PersonViewModel(
    private val repository: PersonRepository,
    application: Application
) : AndroidViewModel(application) {

    // Holder en kontinuerlig strøm av venner fra databasen med flow som sagt
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
