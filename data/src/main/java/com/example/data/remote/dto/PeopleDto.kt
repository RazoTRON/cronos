package com.example.data.remote.dto


import com.example.domain.model.*
import com.example.domain.search.model.*
import com.google.gson.annotations.SerializedName

data class PeopleDto(
    @SerializedName("address_conn")
    val addressConn: List<String>,
    @SerializedName("anketa_conn")
    val anketaConn: List<String>,
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
    @SerializedName("passport_conn")
    val passportConn: List<String>,
    @SerializedName("phone_conn")
    val phoneConn: List<String>,
    val surname: String,
)

fun PeopleDto.toPeople() = People(
    id = this.id,
    peopleId = this.old_id,
    phone = this.login,
    name = this.name,
    surname = this.surname,
    middleName = this.middleName,
    dateOfBirthday = this.dateB,
    key = this.key,
    phoneIdList = this.phoneConn.map { Phone(id = it, phone = "") },
    addressIdList = this.phoneConn.map {
        Address(
            id = it,
            region = "",
            city = "",
            postal = "",
            address = ""
        )
    },
    passportIdList = this.phoneConn.map {
        Passport(
            it,
            date = "",
            by = "",
            id = "",
            inn = "",
            passport = "",
        )
    },
    anketaIdList = this.phoneConn.map {
        Anketa(
            it,
            education = "",
            children = "",
            familyStatus = "",
            workStatus = "",
            workCategory = "",
            workPosition = "",
            workCompany = "",
            living = ""
        )
    },
)