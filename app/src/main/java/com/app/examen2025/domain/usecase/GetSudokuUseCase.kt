package com.app.examen2025.domain.usecase

import com.app.examen2025.domain.common.Result
import com.app.examen2025.domain.model.Sudoku
import com.app.examen2025.domain.repository.Repository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSudokuUseCase
    @Inject
    constructor(
        private val repository: Repository,
    ) {
        operator fun invoke(
            width: Int,
            height: Int,
            difficulty: String,
        ): Flow<Result<Sudoku>> =
            flow {
                try {
                    emit(Result.Loading)
                    val sudoku = repository.getSudoku(width, height, difficulty)
                    emit(Result.Success(sudoku))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
