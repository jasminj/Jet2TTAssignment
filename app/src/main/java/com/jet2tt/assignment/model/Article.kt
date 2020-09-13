package com.jet2tt.assignment.model

import com.google.gson.annotations.SerializedName

data class Article(
	@SerializedName("id") val id: Int,
	@SerializedName("createdAt") val createdAt: String,
	@SerializedName("content") val content: String,
	@SerializedName("comments") val comments: Int,
	@SerializedName("likes") val likes: Int,
	@SerializedName("media") val media: List<Media>,
	@SerializedName("user") val user: List<User>
)