package com.cronos.people_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cronos.ui.theme.CronosTheme
import com.example.domain.search.model.People

@Composable
fun PeopleDetailsScreen(people: People) {
    val vm: PeopleDetailsViewModel = hiltViewModel()

    val peopleDetailsScreenState = vm.peopleDetailsScreenState

    LaunchedEffect(true) {
        vm.findPeople(people.bsonId)
        vm.findPassport(people.peopleId)
        vm.findAddress(people.peopleId)
        vm.findAnketa(people.peopleId)
    }

    PeopleDetailsScreenContent(peopleDetailsScreenState)
}

@Composable
fun PeopleDetailsScreenContent(peopleDetailsScreenState: PeopleDetailsScreenState) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("${peopleDetailsScreenState.people.surname} ${peopleDetailsScreenState.people.name} ${peopleDetailsScreenState.people.middleName}", modifier = Modifier.weight(0.8f))
                Spacer(modifier = Modifier.width(10.dp))
                Text(peopleDetailsScreenState.people.dateOfBirthday)
            }

            Text(text = "Phones:\n" + peopleDetailsScreenState.people.phoneList.plus(peopleDetailsScreenState.people.phone).toSet().joinToString())

            peopleDetailsScreenState.listOfAddress.forEach {
                Column {
                    Text(text = "Region: ${it.region}")
                    Text(text = "City: ${it.city}")
                    Text(text = "Address: ${it.address}, ${it.postal}")
                }
            }

            peopleDetailsScreenState.listOfPassport.forEach {
                Column {
                    Text(text = "INN: ${it.inn} (${it.date})")
                    Text(text = "Organization: ${it.by}")
                    Text(text = "Passport: ${it.passport}")
                }
            }

            peopleDetailsScreenState.listOfAnketa.forEach {
                Column {
                    Text(text = "Family status: ${it.familyStatus}")
                    Text(text = "Children: ${it.children}")
                    Text(text = "Education: ${it.education}")
                    Text(text = "Work status: ${it.workStatus}")
                    Text(text = "Category: ${it.workCategory}")
                    Text(text = "Position: ${it.workPosition}")
                    Text(text = "Work Company: ${it.workCompany}")
                    Text(text = "Living: ${it.living}")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPeopleDetailsScreen() {
    CronosTheme {
        PeopleDetailsScreenContent(PeopleDetailsScreenState())
    }
}