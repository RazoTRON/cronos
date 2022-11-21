package com.cronos.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cronos.login.LoginScreen
import com.cronos.people_details.PeopleDetailsScreen
import com.cronos.search.SearchScreen
import com.cronos.search_result.SearchResultScreen
import com.cronos.util.CronosScreens.*
import com.example.domain.search.model.getPeopleById

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(navController, startDestination = Login.name) {
        composable(Login.name) { LoginScreen(navController) }
        composable(Search.name) { SearchScreen(navController) }
        composable(SearchResult.name) { SearchResultScreen(navController) }
        composable(
            route = "${PeopleDetails.name}/{arg}",
            arguments = listOf(
                navArgument("arg") {
                    type = NavType.StringType
                }
            )
        ) {
            val peopleId = it.arguments?.getString("arg") ?: ""
            val people = SearchInstance.getList().getPeopleById(peopleId)
            PeopleDetailsScreen(people = people)
        }
    }

}