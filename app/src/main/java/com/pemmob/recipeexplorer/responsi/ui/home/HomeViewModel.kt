package com.pemmob.recipeexplorer.responsi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.recipeexplorer.responsi.data.model.Meal
import com.pemmob.recipeexplorer.responsi.data.model.MealResponse
import com.pemmob.recipeexplorer.responsi.data.remote.RetrofitClient
import com.pemmob.recipeexplorer.responsi.data.repository.RecipeRepository
import com.pemmob.recipeexplorer.responsi.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RecipeRepository = RecipeRepositoryImpl(RetrofitClient.apiService)
) : ViewModel() {

    // State untuk menampung daftar resep
    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals.asStateFlow()

    // State untuk indikator loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // State untuk pesan error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // State untuk query pencarian saat ini
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    // State untuk kategori yang dipilih
    private val _selectedCategory = MutableStateFlow<String?>("Chicken")
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        // Initial request tanpa mengubah text di search bar
        searchMeals("Chicken", isInitialOrCategory = true)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
    
    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        _searchQuery.value = "" // kosongkan search bar saat pilih kategori
        fetchMealsByCategory(category)
    }

    fun searchMeals(query: String, isInitialOrCategory: Boolean = false) {
        // Update state query jika pencarian berasal dari search bar
        if (!isInitialOrCategory) {
            _searchQuery.value = query
            _selectedCategory.value = null // hilangkan pilihan kategori saat mencari manual
        }
        
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            // Panggil fungsi search dari repository (mencari berdasarkan NAMA MAKANAN)
            val response = repository.searchMeals(query)
            handleResponse(response)
        }
    }

    private fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            // Panggil fungsi filter berdasarkan kategori (mendukung pencarian "Dessert" dll.)
            val response = repository.filterMealsByCategory(category)
            
            if (response != null) {
                // Endpoint kategori tidak mengembalikan strCategory, jadi kita 'tembel' manual 
                // ke dalam objek agar badge biru muda di card UI tetap muncul
                val mealsWithCategory = response.meals?.map { it.copy(strCategory = category) }
                _meals.value = mealsWithCategory ?: emptyList()
                
                if (_meals.value.isEmpty()) {
                    _errorMessage.value = "Resep dalam kategori ini tidak ditemukan."
                }
            } else {
                _errorMessage.value = "Terjadi kesalahan saat mengambil data dari server."
            }

            _isLoading.value = false
        }
    }

    private fun handleResponse(response: MealResponse?) {
        if (response != null) {
            _meals.value = response.meals ?: emptyList()
            if (response.meals.isNullOrEmpty()) {
                _errorMessage.value = "Resep tidak ditemukan."
            }
        } else {
            _errorMessage.value = "Terjadi kesalahan saat mengambil data dari server."
        }
        _isLoading.value = false
    }
}
