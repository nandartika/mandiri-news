package com.example.mandirinews.data.api

import com.example.mandirinews.BuildConfig
import com.example.mandirinews.data.model.article.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
	@GET("v2/top-headlines")
	suspend fun getNewsByCategory(
		@Query("page") page: Int = 1,
		@Query("category") categoryId: String,
		@Query("q") keyword: String? = null,
		@Query("pageSize") pageSize: Int = 10,
		@Query("language") language: String = "en",
		@Query("apiKey") apiKey: String = BuildConfig.API_KEY
	): Response<NewsResponse>

	@GET("v2/everything")
	suspend fun getNewsBySources(
		@Query("page") page: Int = 1,
		@Query("sources") sources: String? = null,
		@Query("pageSize") pageSize: Int = 10,
		@Query("language") language: String = "en",
		@Query("apiKey") apiKey: String = BuildConfig.API_KEY
	): Response<NewsResponse>
}