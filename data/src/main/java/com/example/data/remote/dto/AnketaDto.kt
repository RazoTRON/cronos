package com.example.data.remote.dto


import com.example.domain.search.model.Anketa
import com.google.gson.annotations.SerializedName

data class AnketaDto(
    @SerializedName("bson_id")
    val bsonId: String,
    val children: String,
    val education: String,
    val familyStatus: String,
    val id: String,
    val living: String,
    val workCategory: String,
    val workCompany: String,
    val workPosition: String,
    val workStatus: String,
)

fun AnketaDto.toAnketa(): Anketa {
    return Anketa(
        id = this.id,
        familyStatus = this.familyStatus,
        education = this.education,
        children = this.children,
        workStatus = this.workStatus,
        workCategory = this.workCategory,
        workPosition = this.workPosition,
        workCompany = this.workCompany,
        living = this.living
    )
}