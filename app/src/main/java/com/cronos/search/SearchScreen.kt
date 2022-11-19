package com.cronos.search

import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cronos.R
import com.cronos.common.composables.StandardTextField
import com.cronos.ui.theme.CronosTheme
import com.cronos.util.CronosScreens
import com.cronos.util.SearchInstance
import com.cronos.util.SearchInstance.asList
import kotlinx.coroutines.currentCoroutineContext

@Composable
fun SearchScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val vm: SearchViewModel = hiltViewModel()

    val context = LocalContext.current

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
                onValueChange = { vm.setPeoplePhone(it) })
            StandardTextField(
                vm.name,
                placeholder = "Name",
                onValueChange = { vm.setPeopleName(it) })
            StandardTextField(
                vm.surname,
                placeholder = "Surname",
                onValueChange = { vm.setPeopleSurname(it) })
            StandardTextField(
                vm.middleName,
                placeholder = "Middle name",
                onValueChange = { vm.setPeopleMiddleName(it) })
            StandardTextField(
                vm.dateOfBirthday,
                placeholder = "Date of birthday",
                onValueChange = { vm.setPeopleDateOfBirthday(it) })
            StandardTextField(vm.inn, placeholder = "INN", onValueChange = { vm.setPeopleInn(it) })
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                Button(onClick = {
                    /*TODO*/
                    vm.saveQuery()
                    if (SearchInstance.getPeopleRequest().asList().filter { it.isNotBlank() }.all { it.contains('*') }) {
                        Toast.makeText(context, "Минимум одно поле должно быть не пустым и не содержать '*'.", Toast.LENGTH_LONG).show()
                    } else {
                        navController.navigate(CronosScreens.SearchResult.name)
                    }
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

