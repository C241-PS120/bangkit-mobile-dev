package com.example.coffeeprotectionandanalysissystem.service

import ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    private const val BASE_URL = "https://model-service-ldfuyzfodq-et.a.run.app/api/v1/"
    private const val BASE_URL_ARTICLE = "https://article-service-ldfuyzfodq-et.a.run.app/api/v1/"
    private const val BASE_URL_WEATHER = "http://api.weatherapi.com/v1/"


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitArticle: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_ARTICLE)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitWeather: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_WEATHER)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
    val articleService: ApiService = retrofitArticle.create(ApiService::class.java)
    val weatherService: ApiService = retrofitWeather.create(ApiService::class.java)
}
