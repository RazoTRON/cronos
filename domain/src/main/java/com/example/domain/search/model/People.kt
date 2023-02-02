package com.example.domain.search.model


data class People(
    val bsonId: String,
    val peopleId: String,
    val phone: String,
    val name: String,
    val surname: String,
    val middleName: String,
    val dateOfBirthday: String,
    val key: String,
    val inn: String,
    val phoneList: List<String>,
)

fun List<People>.getPeopleById(id: String): People {
    return this.first { it.bsonId == id }
}