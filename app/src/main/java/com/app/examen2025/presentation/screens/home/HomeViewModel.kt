package com.app.examen2025.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    //private val getPokemonListUseCase: GetPokemonListUseCase,
) : ViewModel() {
    /*
    // _uiState: flujo interno y mutable
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // init se ejecuta automáticamente cuando se crea el ViewModel.
    init {
        loadPokemonList()
    }
    // Función privada que maneja la lógica de obtención de Pokémon.
    // La volvi publica
    fun loadPokemonList() {
        // Lanza una corrutina
        viewModelScope.launch {
            // Ejecuta el caso de uso y escucha su flujo de resultados
            getPokemonListUseCase().collect { result ->
                // Actualiza el estado de la UI según el resultado
                _uiState.update { state ->
                    when (result) {
                        is Result.Loading ->
                            state.copy(
                                isLoading = true,
                            )
                        is Result.Success ->
                            state.copy(
                                pokemonList = result.data,
                                isLoading = false,
                                error = null,
                            )
                        is Result.Error ->
                            state.copy(
                                error = result.exception.message,
                                isLoading = false,
                            )
                    }
                }
            }
        }
    }
   */
}