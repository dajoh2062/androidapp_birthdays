package com.example.dajoh2062_oblig2.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dajoh2062_oblig2.repositories.PersonRepository

class PersonViewModel : ViewModel() {
    private val personRepository = PersonRepository()
    val personer = personRepository.getPeople()

}