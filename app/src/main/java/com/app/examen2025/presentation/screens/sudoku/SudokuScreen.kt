@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.app.examen2025.presentation.screens.sudoku

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.examen2025.presentation.screens.sudoku.SudokuViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    LaunchedEffect(width, height, difficulty) {
        viewModel.loadSudoku(width, height, difficulty)
    }

    val dimension = width * height
    val boardState: List<List<MutableState<String>>>? =
        remember(sudoku) {
            sudoku?.puzzle?.map { row -> row.map { cell -> mutableStateOf(if (cell == 0) "" else cell.toString()) } }
        }

    val fixed: List<List<Boolean>>? = remember(sudoku) { sudoku?.puzzle?.map { row -> row.map { cell -> cell != 0 } } }

    val incorrect = remember(sudoku) { mutableStateOf(setOf<Pair<Int, Int>>()) }
    val verifyMessage = remember(sudoku) { mutableStateOf<String?>(null) }

    fun doVerify() {
        val b = boardState ?: return
        val sol = sudoku?.solution ?: return
        val incorrectSet = mutableSetOf<Pair<Int, Int>>()
        for (r in sol.indices) {
            for (c in sol[r].indices) {
                val desired = sol[r][c]
                val current = b[r][c].value.toIntOrNull() ?: 0
                if (current != desired) incorrectSet.add(r to c)
            }
        }
        if (incorrectSet.isEmpty()) verifyMessage.value = "Correct!" else verifyMessage.value = "Incorrect: ${incorrectSet.size} cells"
        incorrect.value = incorrectSet
    }

    fun doClean() {
        val b = boardState ?: return
        val f = fixed ?: return
        for (r in b.indices) for (c in b[r].indices) if (!f[r][c]) b[r][c].value = ""
        verifyMessage.value = null
        incorrect.value = emptySet()
    }

    fun doNew() {
        verifyMessage.value = null
        incorrect.value = emptySet()
        viewModel.loadSudoku(width, height, difficulty)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sudoku: ${width}x$height - difficulty=$difficulty") },
                navigationIcon = {
                    IconButton(onClick = onSaveClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    IconButton(onClick = { doVerify() }) {
                        Icon(Icons.Filled.Check, contentDescription = "Verify")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { doClean() }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Clean")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { doNew() }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "New")
                    }
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {
                when {
                    uiState.isLoading -> CircularProgressIndicator()
                    uiState.error != null ->
                        Column {
                            Text(text = "Error: ${uiState.error}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.loadSudoku(width, height, difficulty) }) { Text(text = "Try again") }
                        }

                    sudoku != null && boardState != null && fixed != null -> {
                        Column {
                            for (r in sudoku.puzzle.indices) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                    for (c in sudoku.puzzle[r].indices) {
                                        val isFixed = fixed[r][c]
                                        val cellValue = boardState[r][c].value
                                        val isIncorrect = incorrect.value.contains(r to c)

                                        val bgColor =
                                            when {
                                                isIncorrect -> MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                                                isFixed -> MaterialTheme.colorScheme.surfaceVariant
                                                else -> MaterialTheme.colorScheme.surface
                                            }

                                        Box(modifier = Modifier.padding(2.dp).weight(1f)) {
                                            if (isFixed) {
                                                Surface(
                                                    color = bgColor,
                                                    tonalElevation = 1.dp,
                                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                                ) {
                                                    Box(
                                                        modifier = Modifier.fillMaxSize(),
                                                        contentAlignment = Alignment.Center,
                                                    ) { Text(text = cellValue) }
                                                }
                                            } else {
                                                OutlinedTextField(
                                                    value = cellValue,
                                                    onValueChange = { new ->
                                                        val digits = new.filter { it.isDigit() }
                                                        val maxVal = dimension
                                                        val updated =
                                                            if (digits.isEmpty()) {
                                                                ""
                                                            } else {
                                                                val num = digits.toIntOrNull() ?: 0
                                                                if (num in 1..maxVal) num.toString() else cellValue
                                                            }
                                                        if (updated != cellValue) boardState[r][c].value = updated
                                                        verifyMessage.value = null
                                                        incorrect.value = emptySet()
                                                    },
                                                    singleLine = true,
                                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                                    textStyle = TextStyle(textAlign = TextAlign.Center),
                                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            verifyMessage.value?.let { msg -> Text(text = msg) }
                        }
                    }

                    else ->
                        Column {
                            Text(text = "No sudoku yet. Press Load.")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.loadSudoku(width, height, difficulty) }) { Text(text = "Load") }
                        }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
