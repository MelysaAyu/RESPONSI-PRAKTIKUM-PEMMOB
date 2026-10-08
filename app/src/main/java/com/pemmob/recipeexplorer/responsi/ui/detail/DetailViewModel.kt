package com.pemmob.recipeexplorer.responsi.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.recipeexplorer.responsi.data.model.MealDetail
import com.pemmob.recipeexplorer.responsi.data.remote.RetrofitClient
import com.pemmob.recipeexplorer.responsi.data.repository.RecipeRepository
import com.pemmob.recipeexplorer.responsi.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: RecipeRepository = RecipeRepositoryImpl(RetrofitClient.apiService)
) : ViewModel() {

    private val _mealDetail = MutableStateFlow<MealDetail?>(null)
    val mealDetail: StateFlow<MealDetail?> = _mealDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun getMealDetail(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val response = repository.getMealDetail(id)
            
            if (response != null && !response.meals.isNullOrEmpty()) {
                _mealDetail.value = response.meals[0]
            } else {
                _errorMessage.value = "Detail resep tidak ditemukan."
            }

            _isLoading.value = false
        }
    }
}
