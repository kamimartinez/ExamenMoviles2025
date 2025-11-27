package com.app.examen2025.di

import android.content.Context
import com.app.examen2025.data.remote.api.Api
import com.app.examen2025.data.remote.interceptor.ApiKeyInterceptor
import com.app.examen2025.data.repository.RepositoryImpl
import com.app.examen2025.domain.repository.Repository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiKey(): String = "h5YIFLN9i6AWQz4Y5uY/mg==0dXVebepSHwpm4Ha"

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKey: String): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: Api): Repository = RepositoryImpl(api)
}
