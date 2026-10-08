package com.pemmob.recipeexplorer.responsi.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.ShoppingCart
import com.pemmob.recipeexplorer.responsi.data.model.MealDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    mealId: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel()
) {
    val mealDetail by viewModel.mealDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Memuat data begitu ID diterima atau terjadi perubahan pada ID
    LaunchedEffect(mealId) {
        viewModel.getMealDetail(mealId)
    }

    val SoftPink = Color(0xFFF06292)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Resep", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SoftPink,
                    titleContentColor = Color.White
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                mealDetail != null -> {
                    DetailContent(mealDetail = mealDetail!!)
                }
            }
        }
    }
}

@Composable
fun DetailContent(mealDetail: MealDetail) {
    val scrollState = rememberScrollState()
    val SoftPink = Color(0xFFF06292)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Gambar Utama Resep
        AsyncImage(
            model = mealDetail.strMealThumb,
            contentDescription = mealDetail.strMeal,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Informasi Dasar
            Text(
                text = mealDetail.strMeal ?: "Unknown Recipe",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            // Row untuk Kategori dan Asal berbentuk Outline Box/Chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedCard(
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Kategori: ${mealDetail.strCategory ?: "-"}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                OutlinedCard(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Asal: ${mealDetail.strArea ?: "-"}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Bahan-bahan (Ingredients)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Bahan-bahan",
                    tint = SoftPink
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Bahan-bahan",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            
            val ingredients = mealDetail.getIngredientsWithMeasures()
            ingredients.forEach { (ingredient, measure) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "• $ingredient",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = measure,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Instruksi Memasak (Instructions)
            Text(
                text = "Instruksi",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = mealDetail.strInstructions ?: "Tidak ada instruksi yang tersedia.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * Extension function untuk membantu mengambil bahan (ingredient) beserta takarannya (measure)
 * dan memfilternya dari nilai null atau kosong.
 */
fun MealDetail.getIngredientsWithMeasures(): List<Pair<String, String>> {
    val ingredients = listOf(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
        strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20
    )
    
    val measures = listOf(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
    )

    val validIngredients = mutableListOf<Pair<String, String>>()
    for (i in ingredients.indices) {
        val ingredient = ingredients[i]?.trim()
        val measure = measures[i]?.trim() ?: ""
        
        // Hanya tambahkan jika ingredient-nya tidak kosong/null
        if (!ingredient.isNullOrEmpty()) {
            validIngredients.add(Pair(ingredient, measure))
        }
    }
    return validIngredients
}
