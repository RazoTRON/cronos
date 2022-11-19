package com.example.domain.search.model


data class People(
    val id: String,
    val peopleId: String,
    val phone: String,
    val name: String,
    val surname: String,
    val middleName: String,
    val dateOfBirthday: String,
    val key: String,
    val phoneIdList: List<Phone>,
    val addressIdList: List<Address>,
    val passportIdList: List<Passport>,
    val anketaIdList: List<Anketa>,
) {
    lateinit var phoneList: List<String?>
}

fun List<People>.getPeopleById(id: String): People {
    return this.first { it.id == id }
}