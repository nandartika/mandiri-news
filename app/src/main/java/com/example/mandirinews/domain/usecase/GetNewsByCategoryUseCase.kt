package com.example.mandirinews.domain.usecase

import com.example.mandirinews.data.model.article.NewsResponse
import com.example.mandirinews.data.repository.NewsRepositoryImpl
import com.example.mandirinews.data.util.Resource

class GetNewsByCategoryUseCase(private val newsRepository: NewsRepositoryImpl) {
	suspend fun execute(page: Int, categoryId: String, keyword: String?): Resource<NewsResponse> =
		newsRepository.getNewsByCategory(page, categoryId, keyword)
}