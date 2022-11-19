package com.cronos.login

import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cronos.common.composables.StandardTextField
import com.cronos.ui.theme.CronosTheme
import com.cronos.util.CronosScreens
import com.example.domain.auth.AuthResult

@Composable
fun LoginScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val vm: LoginViewModel = hiltViewModel()

    val context = LocalContext.current

    val androidId = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )

    Log.e("ROUTE", navController.backQueue.joinToString { it.destination.route ?: it.id })

    LaunchedEffect(true) {
        vm.handleStatus()
        vm.send(AuthUiEvent.Authenticate)
        vm.authResult.collect {
            when (it) {
                is AuthResult.Authorized -> {
                    navController.navigate(CronosScreens.Search.name) {
                        popUpTo(CronosScreens.Login.name) {
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, "You're not authorized.", Toast.LENGTH_LONG).show()
                }
                is AuthResult.Error -> {
                    Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

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
                Text(text = "Device ID: $androidId")
                Text(text = "Enter your login and password_")
                StandardTextField(
                    vm.authState.username,
                    placeholder = "Login",
                    onValueChange = { vm.send(AuthUiEvent.UsernameChanged(it)) })
                StandardTextField(
                    vm.authState.password,
                    placeholder = "Password",
                    onValueChange = { vm.send(AuthUiEvent.PasswordChanged(it)) })
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(onClick = {
                            vm.send(AuthUiEvent.SignIn)
//                            /*TODO*/
                        }) {
                            Text(text = "> LOGIN", color = MaterialTheme.colors.onPrimary)
                        }
                        if (vm.statusState) {
                            Button(enabled = vm.statusState, onClick = {
                                vm.send(AuthUiEvent.SignUp)
//                            /*TODO*/
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
fun SearchScreenPreview() {
    CronosTheme {
        LoginScreen(rememberNavController())
    }
}