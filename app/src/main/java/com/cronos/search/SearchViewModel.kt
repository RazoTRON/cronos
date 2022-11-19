package com.cronos.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cronos.util.SearchInstance
import com.example.domain.search.model.request.PeopleRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : ViewModel() {
    var phone by mutableStateOf("")
        private set

    fun setPeoplePhone(phone: String) {
        this.phone = phone
    }

    var name by mutableStateOf("")
        private set

    fun setPeopleName(name: String) {
        this.name = name
    }

    var surname by mutableStateOf("Мигалатюк")
        private set

    fun setPeopleSurname(surname: String) {
        this.surname = surname
    }

    var middleName by mutableStateOf("")
        private set

    fun setPeopleMiddleName(middleName: String) {
        this.middleName = middleName
    }

    var dateOfBirthday by mutableStateOf("")
        private set

    fun setPeopleDateOfBirthday(dateOfBirthday: String) {
        this.dateOfBirthday = dateOfBirthday
    }

    var inn by mutableStateOf("")
        private set

    fun setPeopleInn(inn: String) {
        this.inn = inn
    }

    fun saveQuery() {
        SearchInstance.setPeopleRequest(
            PeopleRequest(
                startId = "",
                peopleId = "",
                enum_id = "",
                phone = phone,
                name = name,
                surname = surname,
                middleName = middleName,
                dateOfBirthday = dateOfBirthday,
                key = "",
                inn = inn
            )
        )
    }

}
