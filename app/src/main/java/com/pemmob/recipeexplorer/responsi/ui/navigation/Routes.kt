package com.pemmob.recipeexplorer.responsi.ui.navigation

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{mealId}"

    fun createDetailRoute(mealId: String): String {
        return "detail/$mealId"
    }
}
