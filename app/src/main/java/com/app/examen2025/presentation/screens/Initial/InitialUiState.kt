package com.app.examen2025.presentation.screens.Initial

data class InitialUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val difficulty: String? = null,
)
