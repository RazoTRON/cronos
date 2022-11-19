package com.cronos.util

import com.example.domain.search.model.People
import com.example.domain.search.model.request.PeopleRequest

object SearchInstance {
    private val list = mutableListOf<People>()
    fun getList() = list

    fun addToList(list: List<People>): Boolean {
        return this.list.addAll(list)
    }
    fun clear() = list.clear()


    private var peopleRequest = PeopleRequest(
        startId = "",
        phone = "",
        peopleId = "",
        enum_id = "",
        name = "",
        surname = "",
        middleName = "",
        dateOfBirthday = "",
        key = "",
        inn = "",
    )
    fun getPeopleRequest() = peopleRequest
    fun setPeopleRequest(request: PeopleRequest): PeopleRequest {
        this.peopleRequest = request
        return this.peopleRequest
    }

    fun PeopleRequest.asList(): List<String> {
        return listOf(phone, name, surname, middleName, dateOfBirthday, inn)
    }

}