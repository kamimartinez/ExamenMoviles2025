package com.app.examen2025.presentation.screens.Sudoku

import com.app.examen2025.domain.model.Sudoku

data class SudokuUiState(
    val isLoading: Boolean = false,
    val sudoku: Sudoku? = null,
    val error: String? = null,
)
