package com.example.data.search

import com.example.data.search.dto.toAddress
import com.example.data.search.dto.toAnketa
import com.example.data.search.dto.toPassport
import com.example.data.search.dto.toPeople
import com.example.domain.search.CronosService
import com.example.domain.search.model.*
import com.example.domain.search.model.request.*
import retrofit2.HttpException

class CronosServiceImpl(val api: CronosApi) : CronosService {
    override suspend fun findPeoples(request: PeopleRequest): List<People> {
        val response = api.findPeoples(
            phone = request.phone,
            startId = request.startId,
            id = request.peopleId,
            enum_id = request.enum_id,
            name = request.name,
            surname = request.surname,
            middleName = request.middleName,
            dateOfBirthday = request.dateOfBirthday.split('.').let { if (it.size > 2) "${it[2]}-${it[1]}-${it[0]}" else it.joinToString(".") },
            key = request.key,
            inn = request.inn
        )
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.map { peopleDto ->
                peopleDto.toPeople()
            }
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun findPassport(request: PassportRequest): List<Passport> {
        val response = api.findPassport(request.id)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.map { it.toPassport() }
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun findAddress(request: AddressRequest): List<Address> {
        val response = api.findAddress(request.id)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.map { it.toAddress() }
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun findAnketa(request: AnketaRequest): List<Anketa> {
        val response = api.findAnketa(request.id)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.map { it.toAnketa() }
        } else {
            throw HttpException(response)
        }
    }
}