package com.cronos.login

import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cronos.common.composables.StandardTextField
import com.cronos.ui.theme.CronosTheme
import com.cronos.util.CronosScreens

@Composable
fun LoginScreen(navController: NavHostController, vm: LoginViewModel = hiltViewModel()) {

    val context = LocalContext.current

    LaunchedEffect(true) {
        vm.handleStatus()
        vm.authenticate()
        vm.authorized.collect {
            if (it) {
                navController.navigate(CronosScreens.Search.name) {
                    popUpTo(CronosScreens.Login.name) {
                        inclusive = true
                    }
                }
            }
        }

    }

    LoginScreenContent(
        screenState = vm.screenState,
        events = vm::onEvent
    )

    LaunchedEffect(vm.screenState.error) {
        if (vm.screenState.error != null) {
            Toast.makeText(
                context,
                vm.screenState.error?.message ?: "Unknown error",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

}

@Composable
fun LoginScreenContent(
    screenState: LoginScreenState,
    events: (UiEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

    val showPassword by remember { mutableStateOf(false) }

    Surface {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Enter your login and password_")
                StandardTextField(
                    value = screenState.username,
                    placeholder = "Login",
                    onValueChange = { events(UiEvent.UsernameChanged(it)) })
                StandardTextField(
                    value = screenState.password,
                    placeholder = "Password",
                    onValueChange = { events(UiEvent.PasswordChanged(it)) },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = { if (showPassword) Icon(Icons.Default.Visibility, null) else Icon(Icons.Default.VisibilityOff, null)}
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(onClick = {
                            events(UiEvent.SignIn)
//                            onSignIn.invoke()
                        }) {
                            Text(text = "> LOGIN", color = MaterialTheme.colors.onPrimary)
                        }
                        if (screenState.register) {
                            Button(onClick = {
                                events(UiEvent.SignUp)
//                                onSignUp.invoke()
                            }) {
                                Text(text = "> REGISTER", color = MaterialTheme.colors.onPrimary)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    CronosTheme {
        LoginScreenContent(screenState = LoginScreenState(
            isLoading = false,
            error = null,
            username = "",
            password = "",
            register = false
        ), events = { })
    }
}


@Composable()
@Preview()
fun PreviewDefault() {
    Test()
    CronosTheme {
    }
}

@Composable
fun Test() {
    var state by remember { mutableStateOf("") }
    Column {
        TextField(
            value = state,
            onValueChange = {
                state = it
            },
            shape = TextFieldDefaults.TextFieldShape
        )
        OutlinedTextField(value = "", onValueChange = {})
    }
}

@Preview
@Composable
fun Test2() {

}