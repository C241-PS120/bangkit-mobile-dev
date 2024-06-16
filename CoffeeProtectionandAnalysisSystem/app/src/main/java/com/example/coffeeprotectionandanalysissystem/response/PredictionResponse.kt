package com.example.coffeeprotectionandanalysissystem.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("symptoms")
	val symptoms: List<String?>? = null,

	@field:SerializedName("search")
	val search: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("suggestion")
	val suggestion: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("label")
	val label: String? = null
)
