package com.example.domain.search

import com.example.domain.search.model.*
import com.example.domain.search.model.request.*

interface CronosService {
    suspend fun findPeoples(request: PeopleRequest): List<People>
    suspend fun getPeople(request: GetPeopleRequest): People
    suspend fun getPassport(request: PassportRequest): List<Passport>
    suspend fun getAddress(request: AddressRequest): List<Address>
    suspend fun getAnketa(request: AnketaRequest): List<Anketa>
}