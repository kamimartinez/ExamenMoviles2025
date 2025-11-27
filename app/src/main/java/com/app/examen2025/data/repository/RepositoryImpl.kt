package com.app.examen2025.data.repository

import com.app.examen2025.data.mapper.toDomain
import com.app.examen2025.data.remote.api.Api
import com.app.examen2025.domain.model.Celebrity
import com.app.examen2025.domain.repository.Repository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class RepositoryImpl
    @Inject
    constructor(
        private val api: Api,
    ) : Repository {
        override suspend fun getCelebrity(name: String): Celebrity {
            val list = api.getCelebrity(name)
            val dto = list.firstOrNull() ?: throw IllegalStateException("No celebrity found for '$name'")
            return dto.toDomain()
        }
    }
