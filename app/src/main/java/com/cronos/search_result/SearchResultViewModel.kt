package com.cronos.search_result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cronos.common.ScreenState
import com.cronos.util.SearchInstance
import com.example.domain.common.Resource
import com.example.domain.search.model.People
import com.example.domain.search.use_case.FindPeoplesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val findPeoplesUseCase: FindPeoplesUseCase,
) : ViewModel() {
    val list = mutableStateListOf<People>()
    var state by mutableStateOf(ScreenState())

    fun findPeople() {
        val startId = list.lastOrNull()?.bsonId ?: ""
        val peopleId = list.lastOrNull()?.peopleId ?: ""

        viewModelScope.launch {
            findPeoplesUseCase.invoke(
                SearchInstance.getPeopleRequest().copy(startId = startId, peopleId = peopleId)
            )
                .collect {
                    when (it) {
                        is Resource.Loading -> state = state.copy(isLoading = true, error = null)
                        is Resource.Success -> {
                            SearchInstance.addToList(it.data!!)
                            list.clear()
                            list.addAll(SearchInstance.getList())
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                error = Resource.Error(
                                    code = it.code,
                                    message = it.message!!
                                )
                            )
                        }
                    }
                }
            state = state.copy(isLoading = false)
        }
    }
}

