package com.cronos.login

import androidx.compose.runtime.State
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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val cronosRepository: CronosRepository,
    private val getStatusUseCase: GetStatusUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val authenticateUseCase: AuthenticateUseCase,
) : ViewModel() {

    var screenState by mutableStateOf(LoginScreenState())
        private set

    private val _authorized = MutableStateFlow(false)
    val authorized: StateFlow<Boolean>
        get() = _authorized


    private fun setUsernameField(username: String) {
        screenState = screenState.copy(username = username)
    }

    private fun setPasswordField(password: String) {
        screenState = screenState.copy(password = password)
    }

    private fun signUp() {
        viewModelScope.launch {
            signUpUseCase.invoke(AuthRequest(screenState.username, screenState.password))
                .handleFlow {
                    signIn()
                }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            signInUseCase.invoke(AuthRequest(screenState.username, screenState.password))
                .handleFlow {
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

    fun handleStatus() {
        viewModelScope.launch {
            getStatusUseCase.invoke(cronosRepository).collect {
                screenState = screenState.copy(register = it)
            }
        }
    }

    private suspend fun <T> Flow<Resource<T>>.handleFlow(onSuccess: suspend () -> Unit) {
        this.collect {
            screenState = when (it) {
                is Resource.Loading -> screenState.copy(isLoading = true)
                is Resource.Success -> {
                    onSuccess.invoke()
                    screenState.copy(isLoading = false, error = null)
                }
                is Resource.Error -> {
                    screenState.copy(
                        isLoading = false,
                        error = Resource.Error(
                            code = it.code,
                            message = it.message ?: "Unknown error."
                        )
                    )
                }
            }
        }
    }


    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.SignIn -> { signIn() }
            is UiEvent.SignUp -> { signUp() }
            is UiEvent.UsernameChanged -> { setUsernameField(uiEvent.value) }
            is UiEvent.PasswordChanged -> { setPasswordField(uiEvent.value) }
        }
    }

}

data class LoginScreenState(
    val isLoading: Boolean = false,
    val error: Resource.Error<Unit>? = null,
    val username: String = "test99",
    val password: String = "test2222",
    val register: Boolean = false,
)

sealed class UiEvent {
    data class UsernameChanged(val value: String) : UiEvent()
    data class PasswordChanged(val value: String) : UiEvent()
    object SignIn : UiEvent()
    object SignUp : UiEvent()
}