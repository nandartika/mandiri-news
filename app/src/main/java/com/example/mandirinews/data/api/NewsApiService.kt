package com.example.mandirinews.data.api

import com.example.mandirinews.BuildConfig
import com.example.mandirinews.data.model.article.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
	@GET("v2/top-headlines")
	suspend fun getTopNewsHeadlines(
		@Query("pageSize") pageSize: Int = 1,
		@Query("language") language: String = "en",
		@Query("apiKey") apiKey: String = BuildConfig.API_KEY
	): Response<APIResponse>
}