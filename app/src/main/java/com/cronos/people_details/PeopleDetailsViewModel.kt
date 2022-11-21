package com.cronos.people_details

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
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
import kotlinx.coroutines.flow.Flow
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
            findPhoneUseCase.invoke(PhoneRequest(id)).handleFlow(listOfPhones)
        }
    }

    fun findAddress(id: String) {
        viewModelScope.launch {
            findAddressUseCase.invoke(id).handleFlow(listOfAddress)
        }
    }

    fun findPassport(id: String) {
        viewModelScope.launch {
            findPassportUseCase.invoke(id).handleFlow(listOfPassport)
        }
    }

    fun findAnketa(id: String) {
        viewModelScope.launch {
            findAnketaUseCase.invoke(id).handleFlow(listOfAnketa)
        }
    }

    suspend fun <T> Flow<Resource<List<T>>>.handleFlow(list: SnapshotStateList<T>) {
        this.collect { data ->
            when (data) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    data.data?.forEach { list.add(it) }
                }
                is Resource.Error -> {}
            }
        }
    }
}