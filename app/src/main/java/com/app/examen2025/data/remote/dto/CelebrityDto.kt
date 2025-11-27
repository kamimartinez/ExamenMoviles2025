package com.app.examen2025.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CelebrityDto(
    @SerializedName("name") val name: String,
    @SerializedName("net_worth") val net_worth: Long,
    @SerializedName("gender") val gender: String,
    @SerializedName("nationality") val nationality: String,
    @SerializedName("occupation") val occupation: List<String>,
    @SerializedName("height") val height: Double,
    @SerializedName("birthday") val birthday: String,
)
