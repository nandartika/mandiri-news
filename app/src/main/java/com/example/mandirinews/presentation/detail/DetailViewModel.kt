package com.example.mandirinews.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mandirinews.data.model.article.Article

class DetailViewModel : ViewModel() {
	private val _article: MutableLiveData<Article> = MutableLiveData()
	val article: LiveData<Article> = _article

	fun setNewsItem(article: Article) {
		_article.value = article
	}
}