package com.app.examen2025.presentation.screens.celebrity

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CelebrityScreen(
    viewModel: CelebrityViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    name: String,
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(name) {
        viewModel.loadCelebrity(name)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Celebrity") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                state.celebrity != null -> {
                    val c = state.celebrity!!
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                    ) {
                        Text(text = c.name, style = MaterialTheme.typography.h6)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Net worth: ${c.net_worth}")
                        Text(text = "Gender: ${c.gender}")
                        Text(text = "Nationality: ${c.nationality}")
                        Text(text = "Occupations: ${c.occupation.joinToString()}")
                        Text(text = "Height (m): ${c.height}")
                        Text(text = "Birthday: ${c.birthday}")
                    }
                }
            }
        }
    }
}
