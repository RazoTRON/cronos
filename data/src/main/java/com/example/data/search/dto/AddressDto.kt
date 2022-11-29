package com.example.data.search.dto


import com.example.domain.search.model.Address
import com.google.gson.annotations.SerializedName

data class AddressDto(
    val address: String,
    @SerializedName("bson_id")
    val bsonId: String,
    val city: String,
    val id: String,
    val postal: String,
    val region: String,
)

fun AddressDto.toAddress(): Address {
    return Address(
        id = this.id,
        region = this.region,
        city = this.city,
        postal = this.postal,
        address = this.address
    )
}