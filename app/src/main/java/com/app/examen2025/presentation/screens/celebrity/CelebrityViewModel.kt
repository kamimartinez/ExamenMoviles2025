package com.app.examen2025.presentation.screens.celebrity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examen2025.domain.usecase.GetCelebrityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.app.examen2025.domain.common.Result
import javax.inject.Inject

@HiltViewModel
class CelebrityViewModel
    @Inject
    constructor(
        private val getCelebrityUseCase: GetCelebrityUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CelebrityUiState())
        val uiState: StateFlow<CelebrityUiState> = _uiState.asStateFlow()

    fun loadCelebrity(name: String) {
        viewModelScope.launch {
            getCelebrityUseCase(name).collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                    is Result.Success -> _uiState.value = _uiState.value.copy(isLoading = false, celebrity = result.data, error = null)
                    is Result.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = result.exception.message ?: "Unknown error")
                }
            }
        }
    }
    }
