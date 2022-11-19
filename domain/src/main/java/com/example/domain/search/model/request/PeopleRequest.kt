package com.example.domain.search.model.request

data class PeopleRequest(
    val startId: String,
    val peopleId: String,
    val enum_id: String,
    val phone: String,
    val name: String,
    val surname: String,
    val middleName: String,
    val dateOfBirthday: String,
    val key: String,
    val inn: String,
)