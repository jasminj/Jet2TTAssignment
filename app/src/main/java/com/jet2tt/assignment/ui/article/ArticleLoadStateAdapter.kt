package com.jet2tt.assignment.ui.article

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ArticleLoadStateAdapter(
        private val retry: () -> Unit
) : LoadStateAdapter<ArticleLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ArticleLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ArticleLoadStateViewHolder {
        return ArticleLoadStateViewHolder.create(parent, retry)
    }
}
