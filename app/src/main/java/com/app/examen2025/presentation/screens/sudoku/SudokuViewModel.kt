package com.app.examen2025.presentation.screens.sudoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examen2025.domain.common.Result
import com.app.examen2025.domain.usecase.GetSudokuUseCase
import com.app.examen2025.domain.usecase.SaveGameUseCase
import com.app.examen2025.domain.usecase.GetSavedGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class SudokuViewModel
    @Inject
    constructor(
        private val getSudokuUseCase: GetSudokuUseCase,
        private val saveGameUseCase: SaveGameUseCase,
        private val getSavedGameUseCase: GetSavedGameUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SudokuUiState())
        val uiState: StateFlow<SudokuUiState> = _uiState.asStateFlow()

        fun loadSudoku(
            width: Int,
            height: Int,
            difficulty: String,
        ) {
            viewModelScope.launch {
                // If there is a saved game that matches requested params, load it first (resume saved game)
                try {
                    val saved = getSavedGameUseCase()
                    if (saved != null && saved.width == width && saved.height == height && saved.difficulty == difficulty) {
                        try {
                            Log.d("SudokuViewModel", "loadSudoku() loading saved game: width=${saved.width} height=${saved.height} difficulty=${saved.difficulty} savedPreview=${saved.current.puzzle.take(3)}")
                        } catch (_: Exception) {}
                        _uiState.value = _uiState.value.copy(isLoading = false, sudoku = saved.current, error = null)
                        return@launch
                    }
                } catch (_: Exception) {
                    // ignore and continue to fetch
                }

                getSudokuUseCase(width, height, difficulty).collect { result ->
                    when (result) {
                        is Result.Loading -> _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                        is Result.Success -> _uiState.value = _uiState.value.copy(isLoading = false, sudoku = result.data, error = null)
                        is Result.Error ->
                            _uiState.value =
                                _uiState.value.copy(isLoading = false, error = result.exception.message ?: "Unknown error")
                    }
                }
            }
        }

        fun saveGame(initial: com.app.examen2025.domain.model.Sudoku, current: com.app.examen2025.domain.model.Sudoku, width: Int, height: Int, difficulty: String) {
            viewModelScope.launch {
                saveGameUseCase(initial, current, width, height, difficulty)
            }
        }

        suspend fun getSavedGame(): com.app.examen2025.domain.model.SavedGame? = getSavedGameUseCase()
    }
