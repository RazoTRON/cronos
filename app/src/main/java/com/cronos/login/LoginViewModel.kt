package com.cronos.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cronos.common.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.domain.auth.AuthRepository
import com.example.domain.auth.AuthRequest
import com.example.domain.auth.AuthResult
import com.example.domain.auth.use_case.AuthenticateUseCase
import com.example.domain.auth.use_case.SignInUseCase
import com.example.domain.auth.use_case.SignUpUseCase
import com.example.domain.common.Resource
import com.example.domain.search.repository.CronosRepository
import com.example.domain.search.use_case.GetStatusUseCase
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

    var authorized by mutableStateOf(false)
        private set

    fun setUsernameField(username: String) {
        this.username = username
    }

    fun setPasswordField(password: String) {
        this.password = password
    }

    fun signUp() {
        viewModelScope.launch {
            signUpUseCase.invoke(
                AuthRequest(
                    username = username,
                    password = password
                )
            ).collect {
                when (it) {
                    is Resource.Loading -> screenState = ScreenState(isLoading = true)
                    is Resource.Success -> {
                        authorized = true
                        screenState = ScreenState()
                    }
                    is Resource.Error -> {
                        screenState = ScreenState(
                            error = Resource.Error(
                                code = it.code,
                                message = it.message ?: "unknown singUp error"
                            )
                        )
                    }
                }
            }


        }
    }

    fun signIn() {
        viewModelScope.launch {
            signInUseCase.invoke(
                AuthRequest(
                    username = username,
                    password = password
                )
            ).collect {
                when (it) {
                    is Resource.Loading -> screenState = ScreenState(isLoading = true)
                    is Resource.Success -> {
                        authorized = true
                        screenState = ScreenState()
                    }
                    is Resource.Error -> {
                        screenState = ScreenState(
                            error = Resource.Error(
                                code = it.code,
                                message = it.message ?: "unknown signIn error"
                            )
                        )
                    }
                }
            }


        }
    }

    fun authenticate() {
        viewModelScope.launch {
            authenticateUseCase.invoke(
                AuthRequest(
                    username = username,
                    password = password
                )
            ).collect {
                when (it) {
                    is Resource.Loading -> screenState = ScreenState(isLoading = true)
                    is Resource.Success -> {
                        authorized = true
                        screenState = ScreenState()
                    }
                    is Resource.Error -> {
                        screenState = ScreenState(
                            error = Resource.Error(
                                code = it.code,
                                message = it.message ?: "unknown authenticate error"
                            )
                        )
                    }
                }
            }
        }
    }

    suspend fun handleStatus() {
        getStatusUseCase.invoke(cronosRepository).collect {
            register = it
        }
    }
}