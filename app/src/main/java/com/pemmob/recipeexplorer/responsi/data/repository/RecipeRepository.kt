package com.pemmob.recipeexplorer.responsi.data.repository

import com.pemmob.recipeexplorer.responsi.data.model.MealDetailResponse
import com.pemmob.recipeexplorer.responsi.data.model.MealResponse

interface RecipeRepository {
    suspend fun searchMeals(query: String): MealResponse?
    suspend fun filterMealsByCategory(category: String): MealResponse?
    suspend fun getMealDetail(id: String): MealDetailResponse?
}
