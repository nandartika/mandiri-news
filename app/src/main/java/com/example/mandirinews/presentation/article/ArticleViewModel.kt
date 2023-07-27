package com.example.mandirinews.presentation.article

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mandirinews.data.model.article.NewsResponse
import com.example.mandirinews.data.util.Resource
import com.example.mandirinews.domain.usecase.GetNewsByCategoryUseCase
import com.example.mandirinews.domain.usecase.GetNewsBySourcesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(
	private val app: Application,
	private val getNewsBySourcesUseCase: GetNewsBySourcesUseCase,
	private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase
) : AndroidViewModel(app) {
	private val _news: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
	val news: LiveData<Resource<NewsResponse>> = _news

	private val _keyword: MutableLiveData<String> = MutableLiveData()
	val keyword: LiveData<String> = _keyword

	private val _categoryId: MutableLiveData<String> = MutableLiveData()
	val categoryId: LiveData<String> = _categoryId

	fun setKeyword(keyword: String) {
		if (_keyword.value != keyword) {
			_keyword.postValue(keyword)
		}
	}

	fun getNewsBySources(page: Int, domains: String?) = viewModelScope.launch(Dispatchers.IO) {
		try {
			if (isNetworkAvailable(app)) {
				_news.postValue(Resource.Loading())
				val apiResult = getNewsBySourcesUseCase.execute(page, domains)
				_news.postValue(apiResult)
			} else {
				_news.postValue(Resource.Error("Network is unavailable"))
			}
		} catch (e: Exception) {
			_news.postValue(Resource.Error(e.message.toString()))
		}
	}

	fun getNewsByCategory(page: Int, categoryId: String, keyword: String?) =
		viewModelScope.launch(Dispatchers.IO) {
			try {
				if (isNetworkAvailable(app)) {
					_news.postValue(Resource.Loading())
					val apiResult = getNewsByCategoryUseCase.execute(page, categoryId, keyword)
					_news.postValue(apiResult)
				} else {
					_news.postValue(Resource.Error("Network is unavailable"))
				}
			} catch (e: Exception) {
				_news.postValue(Resource.Error(e.message.toString()))
			}
		}

	private fun isNetworkAvailable(context: Context?): Boolean {
		if (context == null) return false

		val connectivityManager =
			context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			val networkCapabilities = connectivityManager.activeNetwork ?: return false
			val capabilities = connectivityManager.getNetworkCapabilities(networkCapabilities)
			return capabilities?.run {
				hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(
					NetworkCapabilities.TRANSPORT_WIFI
				) || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
			} ?: false
		} else {
			val activeNetworkInfo = connectivityManager.activeNetworkInfo
			return activeNetworkInfo?.isConnected ?: false
		}
	}
}