package com.jet2tt.assignment.data

import com.jet2tt.assignment.api.ArticleService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class that works with local and remote data sources.
 */
@Singleton
class ArticleRepository @Inject constructor(private val articleService: ArticleService) {

}