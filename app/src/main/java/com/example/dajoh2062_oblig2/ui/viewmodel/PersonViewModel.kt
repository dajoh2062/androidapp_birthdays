/*package com.example.dajoh2062_oblig2.ui.viewmodel

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
*/
package com.example.dajoh2062_oblig2.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dajoh2062_oblig2.data.Person
import com.example.dajoh2062_oblig2.repositories.PersonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PersonViewModel(private val repository:
    PersonRepository,application: Application):
    AndroidViewModel(application) {

    private val _people = MutableStateFlow<List<Person>>(emptyList())
    val people: StateFlow<List<Person>> = _people

    var selectedPerson: Person? by mutableStateOf(null)
        private set

    init {
        // Collect from Room database continuously
        viewModelScope.launch {
            repository.allFriends.collectLatest { persons ->
                _people.value = persons
            }
        }
    }

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

