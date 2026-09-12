package com.mackbex.rickdex.data.di

import com.mackbex.rickdex.data.character.remote.CharacterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  private const val BASE_URL = "https://rickandmortyapi.com/api/"

  @Provides
  @Singleton
  fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

    val retryInterceptor = Interceptor { chain ->
      var response = chain.proceed(chain.request())
      var attempt = 0

      while (response.code == 429 && attempt < 3) {
        val waitSeconds = response.header("retry-after")?.toLongOrNull() ?: 2
        response.close()
        Thread.sleep(waitSeconds * 1000)
        attempt++
        response = chain.proceed(chain.request())
      }
      response
    }

    return OkHttpClient.Builder()
      .addInterceptor(retryInterceptor)
      .addInterceptor(logging)
      .connectTimeout(15, TimeUnit.SECONDS)
      .readTimeout(15, TimeUnit.SECONDS)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit =
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(client)
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .build()

  @Provides
  @Singleton
  fun provideCharacterApi(retrofit: Retrofit): CharacterApi =
    retrofit.create(CharacterApi::class.java)
}