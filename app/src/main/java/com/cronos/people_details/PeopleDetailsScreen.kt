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

    LaunchedEffect(key1 = true) {
        vm.findPassport(people.peopleId)
        vm.findAddress(people.peopleId)
        vm.findAnketa(people.peopleId)
        vm.findPhones(people.peopleId)
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text("${people.surname} ${people.name} ${people.middleName}", modifier = Modifier.weight(0.8f))
                Spacer(modifier = Modifier.width(10.dp))
                Text(people.dateOfBirthday)
            }

            Text(text = "Phones:\n" + vm.listOfPhones.map { it.phone }.plus(people.phone).toSet().joinToString())

            vm.listOfAddress.forEach {
                Column {
                    Text(text = "Region: ${it.region}")
                    Text(text = "City: ${it.city}")
                    Text(text = "Address: ${it.address}, ${it.postal}")
                }
            }

            vm.listOfPassport.forEach {
                Column {
                    Text(text = "INN: ${it.inn} (${it.date})")
                    Text(text = "Organization: ${it.by}")
                    Text(text = "Passport: ${it.passport}")
                }
            }

            vm.listOfAnketa.forEach {
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