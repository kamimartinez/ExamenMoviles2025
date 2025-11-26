package com.app.examen2025.di

import android.content.Context
import com.app.examen2025.data.remote.api.RemplazaApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRemplazaApi(retrofit: Retrofit): RemplazaApi = retrofit.create(RemplazaApi::class.java)

//    @Provides
//    @Singleton
//    fun provideRemplazaPreferences(
//        @ApplicationContext context: Context,
//        gson: Gson,
//    ): RemplazaPreferences = RemplazaPreferences(context, gson)

    @Provides
    @Singleton
    fun provideRemplazaRepository(
        api: RemplazaApi,
//        preferences: RemplazaPreferences,
    ): RemplazaRepository = RemplazaRepositoryImpl(api, preferences)
}
