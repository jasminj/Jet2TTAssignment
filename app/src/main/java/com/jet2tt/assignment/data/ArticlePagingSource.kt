package com.jet2tt.assignment.data

import androidx.paging.PagingSource
import com.jet2tt.assignment.api.ArticleService
import com.jet2tt.assignment.model.Article
import retrofit2.HttpException
import java.io.IOException

private const val ARTICLE_STARTING_PAGE_INDEX = 1
private const val ARTICLE_PAGE_LIMIT = 10

class ArticlePagingSource(private val service: ArticleService) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: ARTICLE_STARTING_PAGE_INDEX
        return try {
            val response = service.getArticles(position, ARTICLE_PAGE_LIMIT)
            LoadResult.Page(
                data = response,
                prevKey = if (position == ARTICLE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
