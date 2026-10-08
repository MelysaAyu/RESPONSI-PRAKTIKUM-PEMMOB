package com.pemmob.recipeexplorer.responsi.data.model

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("idMeal")
    val idMeal: String?,
    @SerializedName("strMeal")
    val strMeal: String?,
    @SerializedName("strMealThumb")
    val strMealThumb: String?,
    @SerializedName("strCategory")
    val strCategory: String?,
    @SerializedName("strArea")
    val strArea: String?
)
