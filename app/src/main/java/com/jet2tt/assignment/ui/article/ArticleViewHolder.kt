package com.jet2tt.assignment.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jet2tt.assignment.R
import com.jet2tt.assignment.databinding.ItemArticleBinding
import com.jet2tt.assignment.model.Article

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
        if (article.media.isNotEmpty()) {
            Glide.with(binding.image)
                .load(article.media[0].image)
                .into(binding.image)
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