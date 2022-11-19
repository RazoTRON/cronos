package com.example.data.remote.dto


import com.example.domain.search.model.Passport
import com.google.gson.annotations.SerializedName

data class PassportDto(
    @SerializedName("bson_id")
    val bsonId: String,
    val by: String,
    val date: String,
    val id: String,
    val inn: String,
    val passport: String
)

fun PassportDto.toPassport(): Passport {
    return Passport(
        bsonId = this.bsonId,
        by = this.by,
        date = this.date,
        id = this.id,
        inn = this.inn,
        passport = this.passport
    )
}