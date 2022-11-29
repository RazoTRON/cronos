package com.example.data.search.dto

import com.example.domain.search.model.*
import com.google.gson.annotations.SerializedName

data class PeopleDto(
    val dateB: String,
    @SerializedName("ecb_id")
    val ecbId: String,
    @SerializedName("enum_id")
    val enumId: String,
    @SerializedName("bson_id")
    val id: String,
    @SerializedName("id")
    val old_id: String,
    val key: String,
    val login: String,
    @SerializedName("middle_name")
    val middleName: String,
    val name: String,
    val inn: String,
    val phoneList: List<String>,
    val surname: String,
)

fun PeopleDto.toPeople() = People(
    id = this.id,
    peopleId = this.old_id,
    phone = this.login,
    name = this.name,
    surname = this.surname,
    middleName = this.middleName,
    dateOfBirthday = this.dateB.split('-').let { if (it.size > 2) "${it[2]}.${it[1]}.${it[0]}" else it.joinToString(".") },
    key = this.key,
    inn = this.inn,
    phoneList = this.phoneList
)