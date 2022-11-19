package com.cronos.search_result.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cronos.ui.theme.CronosTheme
import com.example.domain.search.model.People
import com.example.domain.search.model.Phone

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResultCard(people: People, onClick: () -> Unit) {
    Card(onClick = onClick) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "${people.surname} ${people.name} ${people.middleName}",
                    modifier = Modifier.weight(0.8f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(people.dateOfBirthday)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = people.phone)
            }
        }
    }
}


@Preview
@Composable
fun Preview() {
    CronosTheme {
        ResultCard(
            people = People(
                id = "",
                peopleId = "",
                phone = "380950559022",
                name = "Vladislav",
                surname = "Migalatiuk",
                middleName = "Viktorovich",
                dateOfBirthday = "05.07.1996",
                key = "",
                phoneIdList = listOf(
                    Phone("", "380950559022"),
                    Phone("", "380994735753"),
                    Phone("", "380522341232")
                ),
                addressIdList = listOf(),
                passportIdList = listOf(),
                anketaIdList = listOf(),
            )
        ) {

        }
    }
}