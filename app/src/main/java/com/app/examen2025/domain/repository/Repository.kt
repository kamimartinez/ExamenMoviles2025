package com.app.examen2025.domain.repository

import com.app.examen2025.domain.model.Celebrity

interface Repository {
    suspend fun getCelebrity(name: String): Celebrity
}
