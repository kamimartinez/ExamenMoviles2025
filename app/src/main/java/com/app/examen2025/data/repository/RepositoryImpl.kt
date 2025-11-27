package com.app.examen2025.data.repository

import com.app.examen2025.data.mapper.toDomain
import com.app.examen2025.data.remote.api.Api
import com.app.examen2025.domain.model.Celebrity
import com.app.examen2025.domain.model.Sudoku
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

        override suspend fun getSudoku(
            width: Int,
            height: Int,
            difficulty: String,
        ): Sudoku {
            val list = api.getSudoku(width, height, difficulty)
            return list.firstOrNull() ?: throw IllegalStateException("No sudoku found for $width x $height ($difficulty)")
        }
    }
