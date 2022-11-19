package com.cronos.login

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.cronos.login.AuthUiEvent.*
import com.example.domain.auth.AuthRepository
import com.example.domain.auth.AuthRequest
import com.example.domain.auth.AuthResult
import com.example.domain.auth.use_case.SignUpUseCase
import com.example.domain.common.Resource
import com.example.domain.search.repository.CronosRepository
import com.example.domain.search.use_case.GetStatusUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val cronosRepository: CronosRepository,
    private val getStatusUseCase: GetStatusUseCase,
    private val SignUpUseCase: SignUpUseCase,
) : ViewModel() {

    var authState by mutableStateOf(AuthState())
        private set
    var statusState by mutableStateOf(false)
        private set

    private val authChannel = Channel<AuthResult<Unit>>()
    val authResult = authChannel.receiveAsFlow()

    fun send(data: AuthUiEvent) {
        when (data) {
            is Authenticate -> authenticate()
            is SignUp -> signUp()
            is SignIn -> signIn()
            is UsernameChanged -> authState = authState.copy(username = data.value)
            is PasswordChanged -> authState = authState.copy(password = data.value)
        }
    }

    private fun signUp() {
        viewModelScope.launch {
//            authState = authState.copy(isLoading = true)
//            val result = authRepository.signUp(
//                AuthRequest(
//                    username = authState.username,
//                    password = authState.password
//                )
//            )
//            authChannel.send(result)
//            authState = authState.copy(isLoading = false)

            SignUpUseCase.invoke(
                AuthRequest(
                    username = authState.username,
                    password = authState.password
                )
            ).collect {
                when (it) {
                    is Resource.Loading -> authState = authState.copy(isLoading = true)
                    is Resource.Success -> {
                        authState = authState.copy(isLoading = false)
                        authChannel.send(it.data!!)
                    }
                    is Resource.Error -> {
                        authState = authState.copy(isLoading = false) // TODO
                        authChannel.send(it.data!!)
                    }
                }

            }


        }
    }

    private fun signIn() {
        viewModelScope.launch {
            authState = authState.copy(isLoading = true)
            val result = authRepository.signIn(
                AuthRequest(
                    username = authState.username,
                    password = authState.password
                )
            )
            authChannel.send(result)
            authState = authState.copy(isLoading = false)
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            authState = authState.copy(isLoading = true)
            val result = authRepository.authenticate()
            authChannel.send(result)
            authState = authState.copy(isLoading = false)
        }
    }
//    var login by mutableStateOf("")
//        private set
//
//    fun setUserLogin(login: String) {
//        this.login = login
//    }
//
//    var password by mutableStateOf("")
//        private set
//
//    fun setUserPassword(password: String) {
//        this.password = password
//    }
//
//    fun saveQuery() {
////        SearchInstance.setPeopleRequest(
////            PeopleRequest(
////                startId = "",
////                id = "",
////                enum_id = "",
////                phone = phone,
////                name = name,
////                surname = surname,
////                middleName = middleName,
////                dateOfBirthday = dateOfBirthday,
////                key = "",
////                inn = inn
////            )
////        )
//    }

    suspend fun handleStatus() {
        getStatusUseCase.invoke(cronosRepository).collect {
            statusState = it
        }
    }
}
