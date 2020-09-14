package com.jet2tt.assignment.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jet2tt.assignment.R
import com.jet2tt.assignment.databinding.ItemArticleBinding
import com.jet2tt.assignment.model.Article
import com.jet2tt.assignment.util.Utils

class ArticleViewHolder(private var binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var article: Article? = null

    init {
        binding.root.setOnClickListener {
            article?.let {
            }
        }
    }

    fun bind(article: Article) {
        this.article = article
        val user = article.user[0]

        if (article.media.isNotEmpty()) {
            val media = article.media[0]

            Glide.with(binding.articleImage)
                .load(media.image)
                .into(binding.articleImage)

            binding.articleTitle.text = media.title
            binding.articleUrl.text = media.url
            showViews(binding.articleImage, binding.articleTitle, binding.articleUrl)
        } else {
            hideViews(binding.articleImage, binding.articleTitle, binding.articleUrl)
        }

        Glide.with(binding.userImage)
            .load(user.avatar)
            .into(binding.userImage)

        binding.userName.text = user.name
        binding.userDesignation.text = user.designation
        binding.articleContent.text = article.content
        binding.createdAt.text = Utils.getTimeAgo(article.createdAt)

        binding.articleLikes.text =
            binding.root.resources.getString(
                R.string.likes,
                Utils.abbrevCount(article.likes)
            )
        binding.articleComments.text =
            binding.root.resources.getString(
                R.string.comments,
                Utils.abbrevCount(article.comments)
            )
    }

    private fun showViews(vararg views: View) {
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }

    private fun hideViews(vararg views: View) {
        for (view in views) {
            view.visibility = View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup): ArticleViewHolder {
            val binding = DataBindingUtil.inflate<ItemArticleBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_article, parent, false
            )
            return ArticleViewHolder(binding)
        }
    }
}