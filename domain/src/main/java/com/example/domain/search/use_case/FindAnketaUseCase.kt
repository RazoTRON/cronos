package com.example.domain.search.use_case

import com.example.domain.common.Resource
import com.example.domain.search.model.Anketa
import com.example.domain.search.model.request.AnketaRequest
import com.example.domain.search.repository.CronosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FindAnketaUseCase(val repository: CronosRepository) {
    fun invoke(id: String): Flow<Resource<List<Anketa>>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.findAnketa(
                AnketaRequest(id)
            )
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