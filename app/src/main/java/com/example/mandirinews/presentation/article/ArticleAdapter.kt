package com.example.mandirinews.presentation.article

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mandirinews.data.model.article.Article
import com.example.mandirinews.data.util.dateConverter
import com.example.mandirinews.databinding.ItemArticleCardBinding


class ArticleAdapter(
	val onClickArticle: ((article: Article) -> Unit)? = null,
	val onClickSource: ((source: String?) -> Unit)? = null
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
	private val articles = mutableListOf<Article>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
		val binding =
			ItemArticleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
		val article = articles[position]
		return holder.bind(article)
	}

	override fun getItemCount(): Int = articles.size

	inner class ViewHolder(private val binding: ItemArticleCardBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(article: Article) {
			val source = SpannableString(article.source?.name)
			source.setSpan(UnderlineSpan(), 0, source.length, 0)

			binding.tvTitle.text = article.title
			binding.tvSource.text = source
			binding.tvDate.text = article.publishedAt?.let { dateConverter(it) }
			Glide.with(itemView.context).load(article.urlToImage).into(binding.imageView)

			binding.tvSource.setOnClickListener {
					onClickSource?.invoke(article.source?.id)
			}
			binding.viewArticleItem.setOnClickListener { onClickArticle?.invoke(article) }
			binding.tvTitle.setOnClickListener { onClickArticle?.invoke(article) }
		}
	}

	fun add(articles: List<Article>) {
		this.articles.addAll(articles.toMutableList())
		val positionStart = if (itemCount == 0) 0 else itemCount.minus(1)
		notifyItemRangeInserted(positionStart, articles.size)
	}

	fun clear() {
		val itemCount = articles.size
		articles.clear()
		notifyItemRangeRemoved(0, itemCount)
	}
}