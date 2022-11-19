package com.example.data.remote.dto


import com.example.domain.search.model.Phone
import com.google.gson.annotations.SerializedName

data class PhoneDto(
    @SerializedName("bson_id")
    val bsonId: String,
    val id: String,
    val phone: String
)

fun PhoneDto.toPhone(): Phone {
    return Phone(phone = this.phone, id = this.id)
}