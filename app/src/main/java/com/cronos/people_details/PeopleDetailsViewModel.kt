package com.cronos.people_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.search.model.Address
import com.example.domain.search.model.Anketa
import com.example.domain.search.model.Passport
import com.example.domain.search.model.People
import com.example.domain.search.use_case.FindAddressUseCase
import com.example.domain.search.use_case.FindAnketaUseCase
import com.example.domain.search.use_case.FindPassportUseCase
import com.example.domain.search.use_case.GetPeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    val getPeopleUseCase: GetPeopleUseCase,
    val findPassportUseCase: FindPassportUseCase,
    val findAddressUseCase: FindAddressUseCase,
    val findAnketaUseCase: FindAnketaUseCase,
) : ViewModel() {

    var peopleDetailsScreenState by mutableStateOf(PeopleDetailsScreenState())

    fun findPeople(bsonId: String) {
        viewModelScope.launch {
            getPeopleUseCase.invoke(bsonId).handleFlow {
                it?.let {
                    peopleDetailsScreenState = peopleDetailsScreenState.copy(
                        people = it
                    )
                }
            }
        }
    }
    fun findAddress(id: String) {
        viewModelScope.launch {
            findAddressUseCase.invoke(id).handleFlow {
                it?.let {
                    peopleDetailsScreenState = peopleDetailsScreenState.copy(
                        listOfAddress = it
                    )
                }
            }
        }
    }

    fun findPassport(id: String) {
        viewModelScope.launch {
            findPassportUseCase.invoke(id).handleFlow {
                it?.let {
                    peopleDetailsScreenState = peopleDetailsScreenState.copy(
                        listOfPassport = it
                    )
                }
            }
        }
    }

    fun findAnketa(id: String) {
        viewModelScope.launch {
            findAnketaUseCase.invoke(id).handleFlow {
                it?.let {
                    peopleDetailsScreenState = peopleDetailsScreenState.copy(
                        listOfAnketa = it
                    )
                }
            }
        }
    }

    private suspend fun <T> Flow<Resource<T>>.handleFlow(onSuccess: (T?) -> Unit) {
        this.collect { data ->
            when (data) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    onSuccess(data.data)
                }
                is Resource.Error -> {}
            }
        }
    }
}

data class PeopleDetailsScreenState(
    val people: People = People(
        bsonId = "",
        peopleId = "",
        phone = "",
        name = "",
        surname = "",
        middleName = "",
        dateOfBirthday = "",
        key = "",
        inn = "",
        phoneList = listOf(),
    ),
    val listOfPassport: List<Passport> = listOf(),
    val listOfAddress: List<Address> = listOf(),
    val listOfAnketa: List<Anketa> = listOf(),
)