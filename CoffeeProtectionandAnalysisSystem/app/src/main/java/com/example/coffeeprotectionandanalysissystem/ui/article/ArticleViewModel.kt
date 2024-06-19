package com.example.coffeeprotectionandanalysissystem.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeeprotectionandanalysissystem.response.DataItem
import com.example.coffeeprotectionandanalysissystem.service.ApiConfig
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<DataItem>>()
    val articles: LiveData<List<DataItem>> get() = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _article = MutableLiveData<DataItem?>()
    val article: LiveData<DataItem?> get() = _article

    private val apiService = ApiConfig.articleService

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getAllArticles()
                _articles.value = response.data?.filterNotNull() ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchArticleById(articleId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getArticleById(articleId)
                _article.value = response.data?.firstOrNull()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
