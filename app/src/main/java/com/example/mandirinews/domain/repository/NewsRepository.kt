package com.example.mandirinews.domain.repository

import com.example.mandirinews.data.model.article.NewsResponse
import com.example.mandirinews.data.util.Resource

interface NewsRepository {
	suspend fun getNewsBySources(page: Int, sources: String?): Resource<NewsResponse>
	suspend fun getNewsByCategory(page: Int, categoryId: String, keyword: String?): Resource<NewsResponse>
}