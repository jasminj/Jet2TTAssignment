package com.jet2tt.assignment.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jet2tt.assignment.api.ArticleService
import com.jet2tt.assignment.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class that works with local and remote data sources.
 */
@Singleton
class ArticleRepository @Inject constructor(private val articleService: ArticleService) {

    fun getArticlesResultStream(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { ArticlePagingSource(articleService) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}