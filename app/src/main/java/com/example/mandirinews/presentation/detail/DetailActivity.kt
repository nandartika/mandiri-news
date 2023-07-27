package com.example.mandirinews.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mandirinews.data.model.article.Article
import com.example.mandirinews.data.util.dateConverter
import com.example.mandirinews.databinding.ActivityDetailBinding
import com.example.mandirinews.presentation.base.BackBaseActivity
import com.google.gson.Gson

class DetailActivity : BackBaseActivity() {
	private lateinit var binding: ActivityDetailBinding
	private lateinit var detailViewModel: DetailViewModel

	companion object {
		const val EXTRA_ARTICLE = "ARTICLE"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

		setupObserve()
		getIntentExtra()
	}

	private fun getIntentExtra() {
		val articleJson = intent.getStringExtra(EXTRA_ARTICLE)
		val article = Gson().fromJson(articleJson, Article::class.java)

		article?.let {
			detailViewModel.setNewsItem(it)
		}
	}

	private fun setupObserve() {
		detailViewModel.article.observe(this) {
			Glide.with(this).load(it.urlToImage).into(binding.image)
			binding.tvDate.text = it.publishedAt?.let { it1 -> dateConverter(it1) }
			binding.tvTitle.text = it.title
			binding.tvDescription.text = it.description
		}
	}
}