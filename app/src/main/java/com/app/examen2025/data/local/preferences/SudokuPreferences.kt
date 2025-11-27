package com.app.examen2025.data.local.preferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SudokuPreferences
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val gson: Gson,
    )
