package com.example.mandirinews.data.model.category

import com.example.mandirinews.R


data class Category(val id: String, val name: String, val icon: Int)
object CategoryData {
	val categoryList: List<Category> = listOf(
		Category("business", "Business", R.drawable.ic_business_24),
		Category("entertainment", "Entertainment", R.drawable.ic_camera_24),
		Category("general", "General", R.drawable.ic_newspaper_24),
		Category("health", "Health", R.drawable.ic_health_24),
		Category("science", "Science", R.drawable.ic_science_24),
		Category("sports", "Sport", R.drawable.ic_sports__24),
		Category("technology", "Technology", R.drawable.ic_technology_24)
	)
}