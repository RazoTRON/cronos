package com.cronos.people_details

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.model.*
import com.example.domain.search.model.request.PhoneRequest
import com.example.domain.search.model.Address
import com.example.domain.search.model.Anketa
import com.example.domain.search.model.Passport
import com.example.domain.search.model.Phone
import com.example.domain.search.use_case.FindAddressUseCase
import com.example.domain.search.use_case.FindAnketaUseCase
import com.example.domain.search.use_case.FindPassportUseCase
import com.example.domain.search.use_case.FindPhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    val findPassportUseCase: FindPassportUseCase,
    val findAddressUseCase: FindAddressUseCase,
    val findAnketaUseCase: FindAnketaUseCase,
    val findPhoneUseCase: FindPhoneUseCase,
) : ViewModel() {
    val listOfPassport = mutableStateListOf<Passport>()
    val listOfAddress = mutableStateListOf<Address>()
    val listOfAnketa = mutableStateListOf<Anketa>()
    val listOfPhones = mutableStateListOf<Phone>()


    fun findPhones(id: String) {
        viewModelScope.launch {
            findPhoneUseCase.invoke(PhoneRequest(id)).collect { data ->
                when (data) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        data.data?.forEach { listOfPhones.add(it) }
                    }
                    is Resource.Error -> {
                        listOfPhones.add(
                            Phone(id = "error", phone = "error")
                        )
                    }
                }
            }
        }
    }

    fun findAddress(id: String) {
        viewModelScope.launch {
            findAddressUseCase.invoke(id).collect { data ->
                when (data) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        data.data?.forEach { listOfAddress.add(it) }
                    }
                    is Resource.Error -> {
                        listOfAddress.add(
                            Address(
                                id = "error",
                                region = "error",
                                city = "error",
                                postal = "error",
                                address = "error"
                            )
                        )
                    }
                }
            }
        }
    }


    fun findPassport(id: String) {
        viewModelScope.launch {
            findPassportUseCase.invoke(id).collect { data ->
                when (data) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        data.data?.forEach { listOfPassport.add(it) }
                    }
                    is Resource.Error -> {
                        data.data?.forEach { _ ->
                            listOfPassport.add(
                                Passport(
                                    bsonId = "error",
                                    by = "error",
                                    date = "error",
                                    id = "error",
                                    inn = "error",
                                    passport = "error"
                                )
                            )
                        }
                    }
                }
            }
        }

    }

    fun findAnketa(id: String) {
        viewModelScope.launch {
            findAnketaUseCase.invoke(id).collect { data ->
                when (data) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        data.data?.forEach { listOfAnketa.add(it) }
                    }
                    is Resource.Error -> {
                        listOfAnketa.add(
                            Anketa(
                                id = "error",
                                familyStatus = "error",
                                education = "error",
                                children = "error",
                                workStatus = "error",
                                workCategory = "error",
                                workPosition = "error",
                                workCompany = "error",
                                living = "error"
                            )
                        )
                    }
                }
            }
        }
    }
}