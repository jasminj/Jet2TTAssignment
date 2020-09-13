package com.jet2tt.assignment.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jet2tt.assignment.data.ArticleRepository
import com.jet2tt.assignment.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for the [ArticlesActivity] screen.
 * The ViewModel works with the [ArticleRepository] to get the data.
 */
class ArticleViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {

    private var currentArticleResult: Flow<PagingData<Article>>? = null

    fun getArticles(): Flow<PagingData<Article>> {
        val lastResult = currentArticleResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Article>> = repository.getArticlesResultStream()
            .cachedIn(viewModelScope)
        currentArticleResult = newResult
        return newResult
    }
}