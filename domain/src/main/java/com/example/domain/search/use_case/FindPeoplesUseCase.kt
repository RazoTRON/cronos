package com.example.domain.search.use_case

import com.example.domain.common.Resource
import com.example.domain.search.model.People
import com.example.domain.search.model.request.PeopleRequest
import com.example.domain.search.CronosService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class FindPeoplesUseCase(val repository: CronosService) {

    fun invoke(request: PeopleRequest): Flow<Resource<List<People>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.findPeoples(request)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> emit(Resource.Error(code = 401, "You are not authorized."))
                else -> emit(Resource.Error(
                    code = e.code(),
                    "An unexpected error occurred.\n(${e.localizedMessage})"
                ))
            }

        } catch (e: IOException) {
            if (e is SocketTimeoutException) {
                emit(Resource.Error(code = null,"Please, specify your request."))
            } else {
                emit(
                    Resource.Error(
                        code = null,
                        "Couldn't reach server. Check your internet connection.\n(${e.localizedMessage})"
                    )
                )
            }
        } catch (e: NullPointerException) {
            emit(Resource.Error(code = null, "Please, specify your request.\n${e.localizedMessage}"))
        }
    }
}