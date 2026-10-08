package com.pemmob.recipeexplorer.responsi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pemmob.recipeexplorer.responsi.ui.detail.DetailScreen
import com.pemmob.recipeexplorer.responsi.ui.home.HomeScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToDetail = { mealId ->
                    navController.navigate(Routes.createDetailRoute(mealId))
                }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
            DetailScreen(
                mealId = mealId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
