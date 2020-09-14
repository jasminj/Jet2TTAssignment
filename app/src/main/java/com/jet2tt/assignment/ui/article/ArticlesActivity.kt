package com.jet2tt.assignment.ui.article

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.jet2tt.assignment.R
import com.jet2tt.assignment.databinding.ActivityArticlesBinding
import com.jet2tt.assignment.di.Injectable
import com.jet2tt.assignment.di.injectViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticlesActivity : DaggerAppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ArticleViewModel
    private lateinit var binding: ActivityArticlesBinding
    private val adapter = ArticlesAdapter()

    private var articleJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_articles)
        viewModel = injectViewModel(viewModelFactory)

        initAdapter()
        getArticles()
    }

    private fun initAdapter() {
        binding.articleList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ArticleLoadStateAdapter { adapter.retry() },
            footer = ArticleLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.articleList.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(this, "Oops... ${it.error}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getArticles() {
        // Make sure we cancel the previous job before creating a new one
        articleJob?.cancel()
        articleJob = lifecycleScope.launch {
            viewModel.getArticles().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}