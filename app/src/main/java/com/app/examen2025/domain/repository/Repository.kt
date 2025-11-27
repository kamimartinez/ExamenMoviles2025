package com.app.examen2025.domain.repository

import com.app.examen2025.domain.model.Celebrity
import com.app.examen2025.domain.model.Sudoku

interface Repository {
    suspend fun getCelebrity(name: String): Celebrity

    suspend fun getSudoku(
        width: Int,
        height: Int,
        difficulty: String,
    ): Sudoku
}
