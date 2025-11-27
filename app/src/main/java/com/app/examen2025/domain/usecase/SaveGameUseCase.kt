package com.app.examen2025.domain.usecase

import com.app.examen2025.domain.model.Sudoku
import com.app.examen2025.domain.repository.Repository
import javax.inject.Inject

class SaveGameUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(initial: Sudoku, current: Sudoku, width: Int, height: Int, difficulty: String) {
        repository.saveGame(initial, current, width, height, difficulty)
    }
}
