package com.cronos.search_result

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cronos.common.ScreenState
import com.cronos.search_result.component.ResultCard
import com.cronos.ui.theme.CronosTheme
import com.cronos.util.CronosScreens
import com.example.domain.search.model.People

@Composable
fun SearchResultRoute(
    navController: NavHostController,
    screenState: ScreenState,
    list: SnapshotStateList<People>,
    findPeople: (() -> Unit),
) {

    LaunchedEffect(true) {
        findPeople()
    }

    Log.e("ROUTE", navController.backQueue.joinToString { it.destination.route ?: it.id })

    var maxIndex by rememberSaveable { mutableStateOf(0) }

    Surface {
        if (screenState.error != null) {
            ErrorMessage(text = screenState.error.message.orEmpty())
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(list) { i, people ->
                if (list.size - 10 == i && maxIndex < i) {
                    maxIndex = i
                    findPeople()
                }
                ResultCard(people = people, i + 1) {
                    navController.navigate("${CronosScreens.PeopleDetails.name}/${people.id}")
                }
            }
            if (screenState.isLoading) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}


@Composable
fun SearchResultScreen(navController: NavHostController) {
    val vm: SearchResultViewModel = hiltViewModel()

    SearchResultRoute(
        navController = navController,
        screenState = vm.state,
        list = vm.list,
        findPeople = vm::findPeople
    )
}

@Composable
fun ErrorMessage(text: String) {
    Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.error) {
        Text(text, modifier = Modifier.padding(10.dp))
    }
}

@Preview
@Composable
fun SearchResultScreenPreview() {
    CronosTheme {
        SearchResultRoute(
            rememberNavController(),
            screenState = ScreenState(),
            list = mutableStateListOf(
                People(
                    id = "1",
                    peopleId = "1",
                    phone = "380959559221",
                    name = "Vlad",
                    surname = "Mihalatiuk",
                    middleName = "Viktorovich",
                    dateOfBirthday = "23.12.1996",
                    key = "dfsdfsdf", inn = "323123", phoneList = listOf("380950559022"),
                )
            ),
            findPeople = {}
        )
    }
}