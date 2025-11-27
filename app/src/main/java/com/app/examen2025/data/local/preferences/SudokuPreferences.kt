package com.app.examen2025.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.app.examen2025.data.local.model.SudokuCache
import com.app.examen2025.domain.model.Sudoku
import com.google.gson.Gson
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SudokuPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson,
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(
            PreferencesConstants.PREF_NAME,
            Context.MODE_PRIVATE,
        )

    fun saveLastGame(initial: Sudoku, current: Sudoku, width: Int, height: Int, difficulty: String) {
        val cache = SudokuCache(
            initial = initial,
            current = current,
            width = width,
            height = height,
            difficulty = difficulty,
            lastUpdate = System.currentTimeMillis(),
        )
        val json = gson.toJson(cache)
        Log.d("SudokuPreferences", "saveLastGame() storing cache: $json")
        prefs.edit().putString(PreferencesConstants.KEY_LAST_GAME, json).apply()
        prefs.edit().putLong(PreferencesConstants.KEY_LAST_UPDATE, System.currentTimeMillis()).apply()
    }

    fun getLastGame(): SudokuCache? {
        val json = prefs.getString(PreferencesConstants.KEY_LAST_GAME, null) ?: return null
        return try {
            gson.fromJson(json, SudokuCache::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun hasLastGame(): Boolean = prefs.contains(PreferencesConstants.KEY_LAST_GAME)

    fun clearLastGame() {
        prefs.edit().remove(PreferencesConstants.KEY_LAST_GAME).apply()
        prefs.edit().remove(PreferencesConstants.KEY_LAST_UPDATE).apply()
    }

    fun isCacheValid(): Boolean {
        val last = prefs.getLong(PreferencesConstants.KEY_LAST_UPDATE, 0)
        return System.currentTimeMillis() - last < PreferencesConstants.CACHE_DURATION
    }

    // Last fetched puzzle (used to continue playing offline after a successful fetch)
    fun saveLastFetched(sudoku: Sudoku, width: Int, height: Int, difficulty: String) {
        val cache = SudokuCache(
            initial = sudoku,
            current = sudoku,
            width = width,
            height = height,
            difficulty = difficulty,
            lastUpdate = System.currentTimeMillis(),
        )
        val json = gson.toJson(cache)
        Log.d("SudokuPreferences", "saveLastFetched() storing fetched cache: $json")
        prefs.edit().putString(PreferencesConstants.KEY_LAST_FETCHED, json).apply()
        prefs.edit().putLong(PreferencesConstants.KEY_LAST_UPDATE, System.currentTimeMillis()).apply()
    }

    fun getLastFetched(): SudokuCache? {
        val json = prefs.getString(PreferencesConstants.KEY_LAST_FETCHED, null) ?: return null
        return try {
            gson.fromJson(json, SudokuCache::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun clearLastFetched() {
        prefs.edit().remove(PreferencesConstants.KEY_LAST_FETCHED).apply()
    }
}
