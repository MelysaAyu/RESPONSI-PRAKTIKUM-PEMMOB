package com.pemmob.recipeexplorer.responsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pemmob.recipeexplorer.responsi.ui.navigation.AppNavigation
import com.pemmob.recipeexplorer.responsi.ui.theme.RecipeExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeExplorerTheme {
                // Menghapus Scaffold ganda di sini untuk menghilangkan gap (celah) di TopBar
                AppNavigation()
            }
        }
    }
}
