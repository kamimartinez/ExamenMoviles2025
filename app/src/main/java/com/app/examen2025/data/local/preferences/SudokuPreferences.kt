package com.app.examen2025.data.local.preferences

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SudokuPreferences
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val gson: Gson,
    )
