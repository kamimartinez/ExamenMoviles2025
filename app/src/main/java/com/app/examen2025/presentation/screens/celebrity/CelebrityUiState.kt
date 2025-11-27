package com.app.examen2025.presentation.screens.celebrity

import com.app.examen2025.domain.model.Celebrity

data class CelebrityUiState(
    val isLoading: Boolean = false,
    val celebrity: Celebrity? = null,
    val error: String? = null,
)
