package com.example.domain.search.use_case

import com.example.domain.common.Resource
import com.example.domain.search.model.Phone
import com.example.domain.search.model.request.PhoneRequest
import com.example.domain.search.repository.CronosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FindPhoneUseCase(val repository: CronosRepository) {
    fun invoke(request: PhoneRequest): Flow<Resource<List<Phone>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.findPhone(request)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(code = null, e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: HttpException) {
            emit(Resource.Error(
                code = null,
                e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
            ))
        } catch (e: java.lang.NullPointerException) {
            emit(Resource.Error(code = null, e.localizedMessage ?: "Please, specify your request."))
        }
    }
}