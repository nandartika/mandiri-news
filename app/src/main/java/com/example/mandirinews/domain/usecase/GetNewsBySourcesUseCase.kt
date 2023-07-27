package com.example.mandirinews.domain.usecase

import com.example.mandirinews.data.model.article.NewsResponse
import com.example.mandirinews.data.repository.NewsRepositoryImpl
import com.example.mandirinews.data.util.Resource

class GetNewsBySourcesUseCase(private val newsRepository: NewsRepositoryImpl) {
	suspend fun execute(page: Int, sources: String?): Resource<NewsResponse> =
		newsRepository.getNewsBySources(page, sources)
}