package com.example.coffeeprotectionandanalysissystem.response

data class PredictionResponse(
    val data: PredictionData,
    val message: String,
    val status: String
)

data class PredictionData(
    val id: String,
    val imageUrl: String,
    val label: String,
    val search: String,
    val suggestion: String
)

