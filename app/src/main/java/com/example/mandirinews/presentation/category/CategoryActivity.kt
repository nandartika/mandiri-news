package com.example.mandirinews.presentation.category

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mandirinews.databinding.ActivityCategoryBinding
import com.example.mandirinews.presentation.article.ArticleActivity

class CategoryActivity : AppCompatActivity() {
	private lateinit var binding: ActivityCategoryBinding
	private lateinit var categoryViewModel: CategoryViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCategoryBinding.inflate(layoutInflater)
		setContentView(binding.root)

		categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

		setupView()
	}

	private fun setupView() {
		val categoryList = categoryViewModel.getCategoryData()
		val categoryAdapter =
			CategoryAdapter(categoryList) { categoryId -> moveToArticle(categoryId) }

		binding.rvCategory.layoutManager = LinearLayoutManager(this)
		binding.rvCategory.adapter = categoryAdapter
	}

	private fun moveToArticle(categoryId: String) {
		val intent = Intent(this, ArticleActivity::class.java)
		intent.putExtra(ArticleActivity.EXTRA_CATEGORY_ID, categoryId)
		startActivity(intent)
	}
}