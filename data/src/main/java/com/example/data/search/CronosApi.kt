package com.example.data.search

import com.example.data.search.dto.AddressDto
import com.example.data.search.dto.AnketaDto
import com.example.data.search.dto.PassportDto
import com.example.data.search.dto.PeopleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CronosApi {

    @GET("/api/people/find")
    suspend fun findPeoples(
        @Query("startId") startId: String,
        @Query("id") id: String,
        @Query("enum_id") enum_id: String,
        @Query("phone") phone: String,
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("middleName") middleName: String,
        @Query("dateOfBirthday") dateOfBirthday: String,
        @Query("key") key: String,
        @Query("inn") inn: String,
    ): Response<List<PeopleDto>>

    @GET("/api/people/get")
    suspend fun getPeople(
        @Query("id") bsonId: String
    ): Response<PeopleDto>

    @GET("/api/passport/get")
    suspend fun getPassport(
        @Query("id") id: String
    ): Response<List<PassportDto>>

    @GET("/api/address/get")
    suspend fun getAddress(
        @Query("id") id: String
    ): Response<List<AddressDto>>

    @GET("/api/anketa/get")
    suspend fun getAnketa(
        @Query("id") id: String
    ): Response<List<AnketaDto>>
}
