package com.example.mandirinews.data.model.article

import com.google.gson.annotations.SerializedName

data class ArticleRequest(
	@SerializedName("pageSize") val pageSize: Int = 10,
	@SerializedName("category") val category: String?,
	@SerializedName("language") val language: String = "id",
)