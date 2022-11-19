package com.example.domain.search.use_case

import com.example.domain.common.Resource
import com.example.domain.search.model.People
import com.example.domain.search.model.request.PeopleRequest
import com.example.domain.search.repository.CronosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FindPeoplesUseCase(val repository: CronosRepository) {

    fun invoke(request: PeopleRequest): Flow<Resource<List<People>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.findPeoples(request)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> emit(Resource.Error(code = 401, "You are not authorized."))
                else -> emit(Resource.Error(
                    code = null,
                    e.localizedMessage ?: "An unexpected error occurred."
                ))
            }

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