package com.app.examen2025.domain.model

data class Celebrity(
    val name: String,
    val net_worth: Long,
    val gender: String,
    val nationality: String,
    val occupation: List<String>,
    val height: Double,
    val birthday: String,
)
