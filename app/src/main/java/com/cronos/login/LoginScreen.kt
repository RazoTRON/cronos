package com.cronos.login

import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cronos.common.composables.StandardTextField
import com.cronos.ui.theme.CronosTheme
import com.cronos.util.AppNavigation
import com.cronos.util.CronosScreens
import com.example.data.GlobalNavigationHandler
import com.example.domain.auth.use_case.AuthenticateUseCase
import com.example.domain.auth.use_case.SignInUseCase
import com.example.domain.auth.use_case.SignUpUseCase
import com.example.domain.search.repository.CronosRepository
import com.example.domain.search.use_case.GetStatusUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Composable
fun LoginScreen(navController: NavHostController, vm: LoginViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val androidId = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ANDROID_ID
    )

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

    if (vm.screenState.error != null) {
        Toast.makeText(context, vm.screenState.error?.message ?: "Unknown error", Toast.LENGTH_LONG).show()
    }


}

//@Composable
//fun SearchScreenContent(
//    username: String,
//) {
//    val scrollState = rememberScrollState()
//
//    Surface {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Column(
//                modifier = Modifier
//                    .padding(20.dp)
//                    .verticalScroll(scrollState),
//                verticalArrangement = Arrangement.spacedBy(20.dp)
//            ) {
//                Text(
//                    text = "Login",
//                    style = MaterialTheme.typography.h4,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(text = "Enter your login and password_")
//                StandardTextField(
//                    vm.username,
//                    placeholder = "Login",
//                    onValueChange = { vm.setUsernameField(it) })
//                StandardTextField(
//                    vm.password,
//                    placeholder = "Password",
//                    onValueChange = { vm.setPasswordField(it) })
//                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
//                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
//                        Button(onClick = {
//                            vm.signIn()
//                        }) {
//                            Text(text = "> LOGIN", color = MaterialTheme.colors.onPrimary)
//                        }
//                        if (vm.register) {
//                            Button(enabled = vm.register, onClick = {
//                                vm.signUp()
//                            }) {
//                                Text(text = "> REGISTER", color = MaterialTheme.colors.onPrimary)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@Preview
@Composable
fun SearchScreenPreview() {
    CronosTheme {
        LoginScreen(rememberNavController())
    }
}
