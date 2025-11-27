package com.app.examen2025.domain.usecase

import com.app.examen2025.domain.common.Result
import com.app.examen2025.domain.model.Celebrity
import com.app.examen2025.domain.repository.Repository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCelebrityUseCase
    @Inject
    constructor(
        private val repository: Repository,
    ) {
        operator fun invoke(name: String): Flow<Result<Celebrity>> =
            flow {
                try {
                    emit(Result.Loading)
                    val celebrity = repository.getCelebrity(name)
                    emit(Result.Success(celebrity))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
