package com.example.domain.search.use_case

import com.example.domain.common.Resource
import com.example.domain.search.model.Passport
import com.example.domain.search.model.request.PassportRequest
import com.example.domain.search.CronosService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FindPassportUseCase(val repository: CronosService) {
    fun invoke(id: String): Flow<Resource<List<Passport>>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.findPassport(PassportRequest(id))
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