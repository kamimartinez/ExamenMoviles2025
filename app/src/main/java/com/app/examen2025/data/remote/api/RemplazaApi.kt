package com.app.examen2025.data.remote.api

import com.app.examen2025.data.remote.dto.CelebrityDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("celebrity")
    suspend fun getCelebrity(
        @Query("name") name: String,
    ): List<CelebrityDto>
}
