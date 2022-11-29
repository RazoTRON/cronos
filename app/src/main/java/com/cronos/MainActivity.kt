package com.cronos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cronos.ui.theme.CronosTheme
import com.cronos.util.AppNavigation
import com.cronos.util.CronosScreens
import com.example.domain.util.GlobalNavigationHandler
import com.example.domain.util.GlobalNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), GlobalNavigationHandler {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()

            CronosTheme {
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