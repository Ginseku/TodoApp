package com.example.todoapp.screens.tasks.dialog

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.DAO.CategoryDao
import com.example.todoapp.DAO.CategoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val dao: CategoryDao,
    private val userToken: String
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = dao.getCategoriesForUser(userToken)
        }
    }

    fun addCategory(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            val newCategory = CategoryEntity(name = name, userToken = userToken)
            dao.insertCategory(newCategory)
            loadCategories() // обновляем список
        }
    }
}