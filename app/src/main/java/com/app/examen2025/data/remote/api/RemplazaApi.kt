package com.app.examen2025.data.remote.api

import com.app.examen2025.data.remote.dto.CelebrityDto
import com.app.examen2025.domain.model.Sudoku
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("celebrity")
    suspend fun getCelebrity(
        @Query("name") name: String,
    ): List<CelebrityDto>

    @GET("sudoku")
    suspend fun getSudoku(
        @Query("width") width: Int,
        @Query("height") height: Int,
        @Query("difficulty") difficulty: String,
    ): List<Sudoku>
}
