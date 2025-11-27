package com.app.examen2025.data.local.model

data class SudokuCache(
    val sudokuCache: List<List<Int>>,
    val lastUpdate: Long,
    val offset: Int,
    val totalCount: Int,
)
