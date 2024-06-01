package com.example.coffeeprotectionandanalysissystem.service

import com.example.coffeeprotectionandanalysissystem.response.PredictionResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/api/v1/predict")
    fun getPrediction(
        @Part photo: MultipartBody.Part
    ): Call<PredictionResponse>
}