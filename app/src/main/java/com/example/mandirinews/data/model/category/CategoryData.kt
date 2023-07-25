package com.example.mandirinews.data.model.category


data class Category(val id: String, val name: String, val icon: Int)
object CategoryData {
	val categoryList: List<Category> = listOf(
		Category("business", "Business", 1),
		Category("entertainment", "Entertainment", 2),
		Category("general", "General", 3),
		Category("health", "Health", 4),
		Category("science", "Science", 5),
		Category("sports", "Sport", 6),
		Category("technology", "Technology", 7)
	)
}