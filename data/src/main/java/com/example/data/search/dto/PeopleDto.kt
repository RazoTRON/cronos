package com.example.data.search.dto

import com.example.domain.search.model.*
import com.google.gson.annotations.SerializedName

data class PeopleDto(
    @SerializedName("bson_id")
    val bsonId: String,
//    @SerializedName("ecb_id")
//    val ecbId: String,
    @SerializedName("id")
    val peopleId: String,
    val login: String,
    val phoneList: List<String>,
    val surname: String,
    val name: String,
    @SerializedName("middle_name")
    val middleName: String,
    val dateB: String,
    val key: String,
    val inn: String,
)

fun PeopleDto.toPeople() = People(
    bsonId = this.bsonId,
    peopleId = this.peopleId,
    phone = this.login,
    name = this.name,
    surname = this.surname,
    middleName = this.middleName,
    dateOfBirthday = this.dateB.split('-').let { if (it.size > 2) "${it[2]}.${it[1]}.${it[0]}" else it.joinToString(".") },
    key = this.key,
    inn = this.inn,
    phoneList = this.phoneList
)