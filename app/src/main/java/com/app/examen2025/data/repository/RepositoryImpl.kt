package com.app.examen2025.data.repository

import com.app.examen2025.data.local.preferences.SudokuPreferences
import com.app.examen2025.data.mapper.toDomain
import com.app.examen2025.data.remote.api.Api
import com.app.examen2025.domain.model.Celebrity
import com.app.examen2025.domain.model.Sudoku
import com.app.examen2025.domain.repository.Repository
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl
    @Inject
    constructor(
        private val api: Api,
        private val preferences: SudokuPreferences,
    ) : Repository {
        override suspend fun getCelebrity(name: String): Celebrity {
            val list = api.getCelebrity(name)
            val dto = list.firstOrNull() ?: throw IllegalStateException("No celebrity found for '$name'")
            return dto.toDomain()
        }

        override suspend fun getSudoku(
            width: Int,
            height: Int,
            difficulty: String,
        ): Sudoku {
            return try {
                val dto = api.getSudoku(width, height, difficulty)
                val sudoku = dto.toDomain()
                // persist last fetched puzzle so the app can continue in offline mode
                preferences.saveLastFetched(sudoku, width = width, height = height, difficulty = difficulty)
                sudoku
            } catch (e: Exception) {
                preferences.getLastGame()?.let { cache ->
                    return cache.current
                }
                throw e
            }
        }
        
        override suspend fun saveGame(
            initial: Sudoku,
            current: Sudoku,
            width: Int,
            height: Int,
            difficulty: String,
        ) {
            try {
                // Log a compact preview of what is being saved
                Log.d("RepositoryImpl", "saveGame() called: width=$width height=$height difficulty=$difficulty currentPreview=${current.puzzle.take(3)}")
            } catch (_: Exception) {
            }
            preferences.saveLastGame(initial = initial, current = current, width = width, height = height, difficulty = difficulty)
        }
        
        override suspend fun getSavedGame(): com.app.examen2025.domain.model.SavedGame? {
            return preferences.getLastGame()?.let { cache ->
                com.app.examen2025.domain.model.SavedGame(
                    initial = cache.initial,
                    current = cache.current,
                    width = cache.width,
                    height = cache.height,
                    difficulty = cache.difficulty,
                    lastUpdate = cache.lastUpdate,
                )
            }
        }
    }
