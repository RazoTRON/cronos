package com.cronos.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cronos.common.ScreenState
import com.example.data.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.domain.auth.AuthRepository
import com.example.domain.auth.AuthRequest
import com.example.domain.auth.AuthResponse
import com.example.domain.auth.AuthResult
import com.example.domain.auth.use_case.AuthenticateUseCase
import com.example.domain.auth.use_case.SignInUseCase
import com.example.domain.auth.use_case.SignUpUseCase
import com.example.domain.common.Resource
import com.example.domain.search.repository.CronosRepository
import com.example.domain.search.use_case.GetStatusUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val cronosRepository: CronosRepository,
    private val getStatusUseCase: GetStatusUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val authenticateUseCase: AuthenticateUseCase,
) : ViewModel() {

    var screenState by mutableStateOf(ScreenState())
        private set

    var username by mutableStateOf("test99")
        private set
    var password by mutableStateOf("test2222")
        private set

    var register by mutableStateOf(false)
        private set

    private val _authorized = MutableStateFlow(false)
    val authorized: StateFlow<Boolean>
        get() = _authorized


    fun setUsernameField(username: String) {
        this.username = username
    }

    fun setPasswordField(password: String) {
        this.password = password
    }

    fun signUp() {
        viewModelScope.launch {
            signUpUseCase.invoke(AuthRequest(username, password)).handleFlow {
                signIn()
            }
        }
    }

    fun signIn() {
        viewModelScope.launch {
            signInUseCase.invoke(AuthRequest(username, password)).handleFlow {
                _authorized.emit(true)
            }
        }
    }

    fun authenticate() {
        viewModelScope.launch {
            authenticateUseCase.invoke().handleFlow {
                _authorized.emit(true)
            }
        }
    }

    suspend fun handleStatus() {
        getStatusUseCase.invoke(cronosRepository).collect {
            register = it
        }
    }

    private suspend fun <T> Flow<Resource<T>>.handleFlow(onSuccess: suspend () -> Unit) {
        this.collect {
            screenState = when (it) {
                is Resource.Loading -> ScreenState(isLoading = true)
                is Resource.Success -> {
                    onSuccess.invoke()
                    ScreenState()
                }
                is Resource.Error -> {
                    ScreenState(
                        error = Resource.Error(
                            code = it.code,
                            message = it.message ?: "Unknown error."
                        )
                    )
                }
            }
        }
    }
}

//data class LoginScreenState(
//    val isLoading: Boolean = false,
//    val error: Resource.Error<Unit>? = null,
//    val username: String,
//    val password: String,
//    val register: Boolean
//)