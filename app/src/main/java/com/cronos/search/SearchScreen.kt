package com.cronos.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cronos.common.composables.StandardTextField
import com.cronos.ui.theme.CronosTheme
import com.cronos.util.CronosScreens
import com.cronos.util.SearchInstance

@Composable
fun SearchScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val vm: SearchViewModel = hiltViewModel()

    Log.e("ROUTE", navController.backQueue.joinToString { it.destination.route ?: it.id })

    LaunchedEffect(key1 = true) {
        SearchInstance.clear()
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "Search", style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)
            Text(text = "Enter a query to find some peoples_")
            StandardTextField(
                vm.phone,
                placeholder = "Phone",
                onValueChange = { vm.setPeoplePhone(it.trim()) })
            StandardTextField(
                vm.name,
                placeholder = "Name",
                onValueChange = { name ->
                    vm.setPeopleName(
                        name.trim().lowercase().replaceFirstChar { it.uppercase() })
                })
            StandardTextField(
                vm.surname,
                placeholder = "Surname",
                onValueChange = { surname ->
                    vm.setPeopleSurname(
                        surname.trim().lowercase().replaceFirstChar { it.uppercase() })
                })
            StandardTextField(
                vm.middleName,
                placeholder = "Middle name",
                onValueChange = { midname ->
                    vm.setPeopleMiddleName(
                        midname.trim().lowercase().replaceFirstChar { it.uppercase() })
                })
            StandardTextField(
                vm.dateOfBirthday,
                placeholder = "Date of birthday",
                onValueChange = { vm.setPeopleDateOfBirthday(it.trim()) })
            StandardTextField(
                vm.inn,
                placeholder = "INN",
                onValueChange = { vm.setPeopleInn(it.trim()) })
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                Button(onClick = {
                    /*TODO*/
                    vm.saveQuery()
                    navController.navigate(CronosScreens.SearchResult.name)
                }) {
                    Text(text = "> FIND", color = MaterialTheme.colors.onPrimary)
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    CronosTheme {
        SearchScreen(rememberNavController())
    }
}

