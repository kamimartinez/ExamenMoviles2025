package com.app.examen2025.presentation.screens.initial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examen2025.domain.model.SavedGame
import com.app.examen2025.domain.usecase.GetSavedGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val getSavedGameUseCase: GetSavedGameUseCase,
) : ViewModel() {
    private val _savedGame = MutableStateFlow<SavedGame?>(null)
    val savedGame: StateFlow<SavedGame?> = _savedGame.asStateFlow()

    init {
        viewModelScope.launch {
            val g = getSavedGameUseCase()
            _savedGame.value = g
        }
    }
}
