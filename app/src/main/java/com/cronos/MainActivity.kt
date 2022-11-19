package com.cronos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cronos.common.composables.StandardTextField
import com.cronos.ui.theme.CronosTheme
import com.cronos.util.AppNavigation
import com.cronos.util.CronosScreens
import com.example.data.GlobalNavigationHandler
import com.example.data.GlobalNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), GlobalNavigationHandler {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()

            CronosTheme {
//                val backStackEntry = navController.currentBackStackEntryAsState()
//                val currentScreen = CronosScreens.route(backStackEntry.value?.destination?.route)

                Scaffold {
                    AppNavigation(navController = navController)
                }
            }
        }
    }

    override fun logout() {
        runOnUiThread {
            if (navController.currentDestination?.route != CronosScreens.Login.name) {
                navController.navigate(CronosScreens.Login.name) {
                    popUpTo(navController.currentBackStackEntry?.destination?.parent?.id ?: 0) {
                        inclusive = true
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalNavigator.registerHandler(this)
    }

    override fun onStop() {
        super.onStop()
        GlobalNavigator.unregisterHandler()
    }

}

@Composable
@Preview
fun tesat() {
    CronosTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colors.background
        ) {
            StandardTextField("frrsad", modifier = Modifier.padding(10.dp)) {

            }
        }
    }
}

