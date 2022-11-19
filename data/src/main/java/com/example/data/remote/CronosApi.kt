package com.example.data.remote

import com.example.data.remote.dto.*
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

    @GET("/api/phone/find")
    suspend fun findPhone(
        @Query("peopleId") id: String,
    ): Response<List<PhoneDto>>

    @GET("/api/passport/find")
    suspend fun findPassport(
        @Query("id") id: String
    ): Response<List<PassportDto>>

    @GET("/api/address/find")
    suspend fun findAddress(
        @Query("id") id: String
    ): Response<List<AddressDto>>

    @GET("/api/anketa/find")
    suspend fun findAnketa(
        @Query("id") id: String
    ): Response<List<AnketaDto>>


    @GET("/status")
    suspend fun status(): Response<Unit>
}
