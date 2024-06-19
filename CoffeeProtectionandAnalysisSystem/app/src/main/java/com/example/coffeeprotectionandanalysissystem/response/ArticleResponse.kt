package com.example.coffeeprotectionandanalysissystem.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("success")
    val success: Boolean? = null
)

data class SingleArticleResponse(
    val success: Boolean,
    val data: DataItem?
)

data class DataItem(

    @field:SerializedName("article_id")
    val articleId: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("disease")
    val disease: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("cause")
    val cause: String? = null,

    @field:SerializedName("symptom_summary")
    val symptomSummary: String? = null,

    @field:SerializedName("symptoms")
    val symptoms: List<String>? = null,

    @field:SerializedName("preventions")
    val preventions: List<String>? = null,

    @field:SerializedName("treatments")
    val treatments: Treatments? = null,

    @field:SerializedName("plants")
    val plants: List<String>? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null
)

data class Treatments(

    @field:SerializedName("chemical")
    val chemical: String? = null,

    @field:SerializedName("organic")
    val organic: String? = null
)
