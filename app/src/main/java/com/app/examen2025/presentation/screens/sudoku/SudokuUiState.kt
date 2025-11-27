package com.app.examen2025.presentation.screens.sudoku

import com.app.examen2025.domain.model.Sudoku

data class SudokuUiState(
    val isLoading: Boolean = false,
    val sudoku: Sudoku? = null,
    val error: String? = null,
)
