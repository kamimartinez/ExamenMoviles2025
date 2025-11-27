package com.app.examen2025.domain.model

data class SavedGame(
    val initial: Sudoku,
    val current: Sudoku,
    val width: Int,
    val height: Int,
    val difficulty: String,
    val lastUpdate: Long,
)
