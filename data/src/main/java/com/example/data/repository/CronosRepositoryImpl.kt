package com.example.data.repository

import com.example.data.remote.CronosApi
import com.example.data.remote.dto.*
import com.example.domain.search.repository.CronosRepository
import com.example.domain.search.model.*
import com.example.domain.search.model.request.*
import retrofit2.HttpException

class CronosRepositoryImpl(val api: CronosApi) : CronosRepository {
    override suspend fun findPeoples(request: PeopleRequest): List<People> {
        val response = api.findPeoples(
            phone = request.phone,
            startId = request.startId,
            id = request.peopleId,
            enum_id = request.enum_id,
            name = request.name,
            surname = request.surname,
            middleName = request.middleName,
            dateOfBirthday = request.dateOfBirthday,
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

    override suspend fun findPhone(request: PhoneRequest): List<Phone> {
        val response = api.findPhone(request.id)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.map { it.toPhone() }
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun status() {
        if (!api.status().isSuccessful) throw Exception("Status Error.")
    }
}