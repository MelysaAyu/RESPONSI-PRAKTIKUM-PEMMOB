package com.pemmob.recipeexplorer.responsi.data.repository

import com.pemmob.recipeexplorer.responsi.data.model.MealDetailResponse
import com.pemmob.recipeexplorer.responsi.data.model.MealResponse
import com.pemmob.recipeexplorer.responsi.data.remote.ApiService

class RecipeRepositoryImpl(private val apiService: ApiService) : RecipeRepository {
    override suspend fun searchMeals(query: String): MealResponse? {
        return try {
            apiService.searchMeals(query)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun filterMealsByCategory(category: String): MealResponse? {
        return try {
            apiService.filterMealsByCategory(category)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getMealDetail(id: String): MealDetailResponse? {
        return try {
            apiService.getMealDetail(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
