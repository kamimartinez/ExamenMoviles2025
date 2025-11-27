package com.app.examen2025.presentation.screens.sudoku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun SudokuScreen(
    viewModel: SudokuViewModel = hiltViewModel(),
    onSaveClick: () -> Unit,
    width: Int,
    height: Int,
    difficulty: String,
) {
    val uiState by viewModel.uiState.collectAsState()
    val sudoku = uiState.sudoku

    // Load sudoku when screen is first composed or when parameters change
    LaunchedEffect(width, height, difficulty) {
        viewModel.loadSudoku(width, height, difficulty)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(text = "Sudoku: ${width}x${height} - difficulty=$difficulty")

        Spacer(modifier = Modifier.height(12.dp))

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.error != null -> {
                Text(text = "Error: ${uiState.error}")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.loadSudoku(width, height, difficulty) }) {
                    Text(text = "Try again")
                }
            }

            sudoku != null -> {
                // Simple textual rendering of puzzle and solution for quick verification
                Text(text = "Puzzle:")
                Text(text = formatGrid(sudoku.puzzle))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Solution:")
                Text(text = formatGrid(sudoku.solution))

                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { viewModel.loadSudoku(width, height, difficulty) }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Refresh")
                }
            }

            else -> {
                Text(text = "No sudoku yet. Press Refresh.")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.loadSudoku(width, height, difficulty) }) {
                    Text(text = "Load")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSaveClick) {
            Text(text = "Back")
        }
    }
}

private fun formatGrid(grid: List<List<Int>>): String =
    grid.joinToString(separator = "\n") { row ->
        row.joinToString(separator = " ") { cell ->
            when (cell) {
                0 -> "."
                else -> cell.toString()
            }
        }
    }

