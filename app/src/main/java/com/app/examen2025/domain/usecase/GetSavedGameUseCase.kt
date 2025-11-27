package com.app.examen2025.domain.usecase

import com.app.examen2025.domain.model.SavedGame
import com.app.examen2025.domain.repository.Repository
import javax.inject.Inject

class GetSavedGameUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): SavedGame? = repository.getSavedGame()
}
