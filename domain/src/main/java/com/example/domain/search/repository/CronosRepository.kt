package com.example.domain.search.repository

import com.example.domain.search.model.*
import com.example.domain.search.model.request.*

interface CronosRepository {
    suspend fun findPeoples(request: PeopleRequest): List<People>
    suspend fun findPassport(request: PassportRequest): List<Passport>
    suspend fun findAddress(request: AddressRequest): List<Address>
    suspend fun findAnketa(request: AnketaRequest): List<Anketa>
    suspend fun findPhone(request: PhoneRequest): List<Phone>
    suspend fun status(): Unit
}