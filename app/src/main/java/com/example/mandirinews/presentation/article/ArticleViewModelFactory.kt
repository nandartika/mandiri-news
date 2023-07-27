package com.example.mandirinews.presentation.article

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mandirinews.domain.usecase.GetNewsByCategoryUseCase
import com.example.mandirinews.domain.usecase.GetNewsBySourcesUseCase

class ArticleViewModelFactory(
	private val app: Application,
	private val getNewsBySourcesUseCase: GetNewsBySourcesUseCase,
	private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase
) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return ArticleViewModel(app, getNewsBySourcesUseCase, getNewsByCategoryUseCase) as T
	}
}