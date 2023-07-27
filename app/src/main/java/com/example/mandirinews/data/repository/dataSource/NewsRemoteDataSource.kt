package com.example.mandirinews.data.repository.dataSource

import com.example.mandirinews.data.model.article.NewsResponse
import retrofit2.Response

interface NewsRemoteDataSource {
	suspend fun getNewsBySources(page: Int, sources: String?): Response<NewsResponse>
	suspend fun getNewsByCategory(page: Int, categoryId: String, keyword: String?): Response<NewsResponse>

}