package com.example.mandirinews.presentation.category

import androidx.lifecycle.ViewModel
import com.example.mandirinews.data.model.category.Category
import com.example.mandirinews.data.model.category.CategoryData

class CategoryViewModel : ViewModel() {
	fun getCategoryData(): List<Category> = CategoryData.categoryList
}