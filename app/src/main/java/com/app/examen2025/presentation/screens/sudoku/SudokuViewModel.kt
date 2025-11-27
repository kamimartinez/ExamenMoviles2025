package com.app.examen2025.presentation.screens.sudoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examen2025.domain.common.Result
import com.app.examen2025.domain.usecase.GetSudokuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel
    @Inject
    constructor(
        private val getSudokuUseCase: GetSudokuUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SudokuUiState())
        val uiState: StateFlow<SudokuUiState> = _uiState.asStateFlow()

        fun loadSudoku(
            width: Int,
            height: Int,
            difficulty: String,
        ) {
            viewModelScope.launch {
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
    }
