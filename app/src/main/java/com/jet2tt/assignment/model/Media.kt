package com.jet2tt.assignment.model

import com.google.gson.annotations.SerializedName

data class Media(
	@SerializedName("id") val id: Int,
	@SerializedName("blogId") val blogId: Int,
	@SerializedName("createdAt") val createdAt: String,
	@SerializedName("image") val image: String,
	@SerializedName("title") val title: String,
	@SerializedName("url") val url: String
)