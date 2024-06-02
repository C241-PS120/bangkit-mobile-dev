package com.example.coffeeprotectionandanalysissystem.response

data class PredictionResponse(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
)

data class Data(
	val search: String? = null,
	val imageUrl: String? = null,
	val suggestion: String? = null,
	val id: String? = null,
	val label: String? = null
)

