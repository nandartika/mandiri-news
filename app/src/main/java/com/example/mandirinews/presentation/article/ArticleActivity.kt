package com.example.mandirinews.presentation.article

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mandirinews.data.api.NewsApiService
import com.example.mandirinews.data.api.RetrofitInstance
import com.example.mandirinews.data.model.article.Article
import com.example.mandirinews.data.repository.NewsRepositoryImpl
import com.example.mandirinews.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import com.example.mandirinews.data.util.DialogManager
import com.example.mandirinews.data.util.ProgressBarUtil
import com.example.mandirinews.data.util.Resource
import com.example.mandirinews.databinding.ActivityArticleBinding
import com.example.mandirinews.domain.usecase.GetNewsByCategoryUseCase
import com.example.mandirinews.domain.usecase.GetNewsBySourcesUseCase
import com.example.mandirinews.presentation.detail.DetailActivity
import com.google.gson.Gson

class ArticleActivity : AppCompatActivity() {
	private lateinit var binding: ActivityArticleBinding
	private lateinit var articleViewModel: ArticleViewModel
	private var articleAdapter = ArticleAdapter({ article -> moveToDetailActivity(article) },
		{ source -> showArticlesBySource(source) })
	private var isLoading = false
	private var page = 1
	private var categoryId: String? = null
	private val progressBarUtil by lazy { ProgressBarUtil(this) }
	private var keyword: String? = null
	private var totalArticles: Int = 0
	private var source: String? = null
	private val dialogManager by lazy { DialogManager(this) }

	companion object {
		const val EXTRA_CATEGORY_ID = "CATEGORY_ID"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityArticleBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setupViewModel()
		setupView()
		setupObserve()
	}

	private fun setupViewModel() {
		val retService = RetrofitInstance.getRetrofitInstance().create(NewsApiService::class.java)
		val remoteDataSource = NewsRemoteDataSourceImpl(retService)
		val repository = NewsRepositoryImpl(remoteDataSource)

		val getNewsBySourcesUseCase = GetNewsBySourcesUseCase(repository)
		val getNewsByCategoryUseCase = GetNewsByCategoryUseCase(repository)

		val factory = ArticleViewModelFactory(
			application, getNewsBySourcesUseCase, getNewsByCategoryUseCase
		)
		articleViewModel = ViewModelProvider(this, factory)[ArticleViewModel::class.java]
	}

	private fun setupView() {
		binding.searchView.setOnQueryTextListener(onQueryTextListener)

		binding.rvArticle.layoutManager = LinearLayoutManager(this)
		binding.rvArticle.adapter = articleAdapter
		binding.rvArticle.addOnScrollListener(onScrollListener)
	}

	private fun setupObserve() {
		categoryId = intent.getStringExtra(EXTRA_CATEGORY_ID)

		articleViewModel.keyword.observe(this) {
			this.keyword = it
			page = 1
			articleAdapter.clear()
			categoryId?.let { id -> articleViewModel.getNewsByCategory(page, id, it) }
		}

		articleViewModel.news.observe(this) { response ->
			when (response) {
				is Resource.Success -> {
					isLoading = false
					totalArticles = response.data?.totalResults ?: 0
					progressBarUtil.hideProgressBar()
					response.data?.articles?.let { articleAdapter.add(it) }
				}

				is Resource.Loading -> {
					isLoading = true
					progressBarUtil.showProgressBar()
				}

				is Resource.Error -> {
					isLoading = false
					progressBarUtil.hideProgressBar()

					response.message?.let {
						dialogManager.showSingleButtonDialog(it)
					}
				}
			}
		}

		categoryId?.let { articleViewModel.getNewsByCategory(page, it, keyword) }
	}

	private fun moveToDetailActivity(article: Article) {
		val intent = Intent(this, DetailActivity::class.java)
		val articleJson = Gson().toJson(article)
		intent.putExtra(DetailActivity.EXTRA_ARTICLE, articleJson)
		startActivity(intent)
	}

	private fun showArticlesBySource(source: String?) {
		if (source != null) {
			page = 1
			this.source = source
			articleAdapter.clear()
			articleViewModel.getNewsBySources(page, source)
		} else {
			dialogManager.showSingleButtonDialog("Source Tidak Tersedia")
		}
	}

	private val onScrollListener = object : RecyclerView.OnScrollListener() {
		override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
			super.onScrolled(recyclerView, dx, dy)
			if (isLoading) return

			(recyclerView.layoutManager as? LinearLayoutManager?)?.let {
				val sizeOfTheCurrentList = it.itemCount
				val visibleItems = it.childCount
				val topPosition = it.findFirstVisibleItemPosition()

				val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
				val hasReachAllList = articleAdapter.itemCount >= totalArticles
				val shouldPaginate = !isLoading && hasReachedToEnd && !hasReachAllList

				if (shouldPaginate) {
					page++
					if (source != null) {
						articleViewModel.getNewsBySources(page, source)
					} else {
						categoryId?.let { id ->
							articleViewModel.getNewsByCategory(
								page,
								id,
								keyword
							)
						}
					}
				}
			}
		}
	}

	private val onQueryTextListener = object : SearchView.OnQueryTextListener {
		override fun onQueryTextSubmit(query: String?): Boolean {
			query?.let { articleViewModel.setKeyword(it) }
			return false
		}

		override fun onQueryTextChange(newText: String?): Boolean {
			return false
		}
	}
}