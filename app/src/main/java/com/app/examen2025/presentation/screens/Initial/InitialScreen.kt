package com.app.examen2025.presentation.screens.Initial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InitialScreen(
    onSaveClick: (width: Int, height: Int, difficulty: String) -> Unit,
) {
    val widthOptions = listOf(2, 3, 4)
    val heightOptions = listOf(2, 3, 4)
    val difficultyOptions = listOf("easy", "medium", "hard")

    var selectedWidth by remember { mutableStateOf(widthOptions.first()) }
    var expandedWidth by remember { mutableStateOf(false) }

    var selectedHeight by remember { mutableStateOf(heightOptions.first()) }
    var expandedHeight by remember { mutableStateOf(false) }

    var selectedDifficulty by remember { mutableStateOf(difficultyOptions.first()) }
    var expandedDifficulty by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Select Sudoku parameters", style = MaterialTheme.typography.h6)

        // Width dropdown
        ExposedDropdownMenuBox(expanded = expandedWidth, onExpandedChange = { expandedWidth = !expandedWidth }) {
            TextField(
                value = selectedWidth.toString(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Width") },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expandedWidth, onDismissRequest = { expandedWidth = false }) {
                widthOptions.forEach { option ->
                    DropdownMenuItem(onClick = {
                        selectedWidth = option
                        expandedWidth = false
                    }) {
                        Text(option.toString())
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        // Height dropdown
        ExposedDropdownMenuBox(expanded = expandedHeight, onExpandedChange = { expandedHeight = !expandedHeight }) {
            TextField(
                value = selectedHeight.toString(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Height") },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expandedHeight, onDismissRequest = { expandedHeight = false }) {
                heightOptions.forEach { option ->
                    DropdownMenuItem(onClick = {
                        selectedHeight = option
                        expandedHeight = false
                    }) {
                        Text(option.toString())
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        // Difficulty dropdown
        ExposedDropdownMenuBox(expanded = expandedDifficulty, onExpandedChange = { expandedDifficulty = !expandedDifficulty }) {
            TextField(
                value = selectedDifficulty,
                onValueChange = {},
                readOnly = true,
                label = { Text("Difficulty") },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expandedDifficulty, onDismissRequest = { expandedDifficulty = false }) {
                difficultyOptions.forEach { option ->
                    DropdownMenuItem(onClick = {
                        selectedDifficulty = option
                        expandedDifficulty = false
                    }) {
                        Text(option)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = { onSaveClick(selectedWidth, selectedHeight, selectedDifficulty) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Save and Open Sudoku")
        }
    }
}
