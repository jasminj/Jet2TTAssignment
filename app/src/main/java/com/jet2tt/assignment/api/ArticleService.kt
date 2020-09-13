package com.jet2tt.assignment.api

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Articles REST API access points
 */
interface ArticleService {

    companion object {
        const val ENDPOINT = "https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1"
    }

    @GET("/blogs?page=1&limit=10")
    suspend fun getArticles(
        @Path("page") page: Int
    ): Any
}