package com.jet2tt.assignment.api

import com.jet2tt.assignment.model.Article
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Blog Articles REST API access points
 */
interface ArticleService {

    companion object {
        const val ENDPOINT = "https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1/"
    }

    @GET("blogs")
    suspend fun getArticles(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<Article>
}