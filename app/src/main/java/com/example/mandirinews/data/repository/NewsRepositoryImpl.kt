package com.example.mandirinews.data.repository

import com.example.mandirinews.data.model.article.NewsResponse
import com.example.mandirinews.data.repository.dataSource.NewsRemoteDataSource
import com.example.mandirinews.data.util.Resource
import com.example.mandirinews.domain.repository.NewsRepository
import retrofit2.Response

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource) : NewsRepository {
	override suspend fun getNewsBySources(page: Int, sources: String?): Resource<NewsResponse> {
		return responseToResource(newsRemoteDataSource.getNewsBySources(page, sources))
	}

	override suspend fun getNewsByCategory(
		page: Int,
		categoryId: String,
		keyword: String?
	): Resource<NewsResponse> {
		return responseToResource(newsRemoteDataSource.getNewsByCategory(page, categoryId, keyword))
	}

	private fun responseToResource(response: Response<NewsResponse>): Resource<NewsResponse> {
		if (response.isSuccessful) {
			response.body()?.let { result ->
				return Resource.Success(result)
			}
		}
		val message = when (response.code()) {
			400 -> "Bad Request"
			401 -> "Not Authorized"
			500 -> "Server Error"
			else -> "Unknown Error"
		}
		return Resource.Error(message)
	}
}