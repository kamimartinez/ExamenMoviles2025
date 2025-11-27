package com.app.examen2025.data.remote.dto

data class SudokuDto(
    // API returns `null` for empty cells, so use nullable Int
    val puzzle: List<List<Int?>>,
    val solution: List<List<Int?>>,
)
