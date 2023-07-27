package com.example.mandirinews.data.repository.dataSourceImpl

import com.example.mandirinews.data.api.NewsApiService
import com.example.mandirinews.data.model.article.NewsResponse
import com.example.mandirinews.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsApiService: NewsApiService) : NewsRemoteDataSource {
	override suspend fun getNewsBySources(page: Int, sources: String?): Response<NewsResponse> {
		return newsApiService.getNewsBySources(page, sources)
	}

	override suspend fun getNewsByCategory(
		page: Int,
		categoryId: String,
		keyword: String?
	): Response<NewsResponse> {
		return newsApiService.getNewsByCategory(page, categoryId, keyword)
	}
}