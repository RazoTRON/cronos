package com.cronos.search_result.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cronos.ui.theme.CronosTheme
import com.example.domain.search.model.People

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResultCard(people: People, counter: Int, onClick: () -> Unit) {
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
                Text(text = "$counter", color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f))
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
                bsonId = "",
                peopleId = "",
                phone = "380950559022",
                name = "Vladislav",
                surname = "Migalatiuk",
                middleName = "Viktorovich",
                dateOfBirthday = "05.07.1996",
                key = "", inn = "", phoneList = listOf("380950559022"),
            ),
            counter = 4
        ) {

        }
    }
}