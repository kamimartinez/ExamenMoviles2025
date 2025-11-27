package com.app.examen2025.data.mapper

import com.app.examen2025.data.remote.dto.SudokuDto
import com.app.examen2025.domain.model.Sudoku

fun SudokuDto.toDomain(): Sudoku =
    Sudoku(
        puzzle = this.puzzle,
        solution = this.solution,
    )
