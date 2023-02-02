package com.example.domain.search.use_case

import com.example.domain.common.Resource
import com.example.domain.search.CronosService
import com.example.domain.search.model.People
import com.example.domain.search.model.request.GetPeopleRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class GetPeopleUseCase(val repository: CronosService) {
    fun invoke(bsonId: String): Flow<Resource<People>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.getPeople(GetPeopleRequest(bsonId = bsonId))
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(code = null, e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    code = null,
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
                )
            )
        } catch (e: NullPointerException) {
            emit(Resource.Error(code = null, e.localizedMessage ?: "Please, specify your request."))
        }
    }
}