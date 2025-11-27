@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.app.examen2025.presentation.screens.initial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InitialScreen(onSaveClick: (width: Int, height: Int, difficulty: String) -> Unit) {
    val widthOptions = listOf(2, 3, 4)
    val heightOptions = listOf(2, 3, 4)
    val difficultyOptions = listOf("easy", "medium", "hard")

    var selectedWidth by remember { mutableStateOf(widthOptions.first()) }
    var expandedWidth by remember { mutableStateOf(false) }

    var selectedHeight by remember { mutableStateOf(heightOptions.first()) }
    var expandedHeight by remember { mutableStateOf(false) }

    var selectedDifficulty by remember { mutableStateOf(difficultyOptions.first()) }
    var expandedDifficulty by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                item {
                    Text(
                        text = "Select Sudoku parameters",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // Width dropdown
                item {
                    ExposedDropdownMenuBox(
                        expanded = expandedWidth,
                        onExpandedChange = { expandedWidth = !expandedWidth },
                    ) {
                        OutlinedTextField(
                            value = selectedWidth.toString(),
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Width") },
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWidth) },
                        )
                        ExposedDropdownMenu(
                            expanded = expandedWidth,
                            onDismissRequest = { expandedWidth = false },
                        ) {
                            widthOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.toString()) },
                                    onClick = {
                                        selectedWidth = option
                                        expandedWidth = false
                                    },
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Height dropdown
                item {
                    ExposedDropdownMenuBox(
                        expanded = expandedHeight,
                        onExpandedChange = { expandedHeight = !expandedHeight },
                    ) {
                        OutlinedTextField(
                            value = selectedHeight.toString(),
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Height") },
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedHeight) },
                        )
                        ExposedDropdownMenu(
                            expanded = expandedHeight,
                            onDismissRequest = { expandedHeight = false },
                        ) {
                            heightOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.toString()) },
                                    onClick = {
                                        selectedHeight = option
                                        expandedHeight = false
                                    },
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Difficulty dropdown
                item {
                    ExposedDropdownMenuBox(
                        expanded = expandedDifficulty,
                        onExpandedChange = { expandedDifficulty = !expandedDifficulty },
                    ) {
                        OutlinedTextField(
                            value = selectedDifficulty,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Difficulty") },
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDifficulty) },
                        )
                        ExposedDropdownMenu(
                            expanded = expandedDifficulty,
                            onDismissRequest = { expandedDifficulty = false },
                        ) {
                            difficultyOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        selectedDifficulty = option
                                        expandedDifficulty = false
                                    },
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Button(
                        onClick = {
                            onSaveClick(
                                selectedWidth,
                                selectedHeight,
                                selectedDifficulty,
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
