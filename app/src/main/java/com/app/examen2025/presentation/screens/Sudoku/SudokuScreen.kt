package com.app.examen2025.presentation.screens.Sudoku

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SudokuScreen(
    viewModel: SudokuViewModel = hiltViewModel(),
    onSaveClick: () -> Unit,
    width: Int,
    height: Int,
    difficulty: String,
) {
    val state by viewModel.uiState.collectAsState()
}
