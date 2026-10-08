package com.pemmob.recipeexplorer.responsi.data.remote

import com.pemmob.recipeexplorer.responsi.data.model.MealDetailResponse
import com.pemmob.recipeexplorer.responsi.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): MealResponse

    @GET("filter.php")
    suspend fun filterMealsByCategory(
        @Query("c") category: String
    ): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") id: String
    ): MealDetailResponse
    
    companion object {
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }
}
