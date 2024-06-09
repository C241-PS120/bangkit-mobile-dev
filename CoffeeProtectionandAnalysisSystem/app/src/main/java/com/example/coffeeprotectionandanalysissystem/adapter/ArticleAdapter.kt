package com.example.coffeeprotectionandanalysissystem.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityListArticleBinding
import com.example.coffeeprotectionandanalysissystem.response.DataItem
import android.widget.Filter
import android.widget.Filterable
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.view.detail.DetailArtikelActivity
import java.text.SimpleDateFormat
import java.util.Locale

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(), Filterable {

    private var articleList: List<DataItem> = listOf()
    private var filteredArticleList: List<DataItem> = listOf()

    fun setArticles(articles: List<DataItem>) {
        articleList = articles
        filteredArticleList = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ActivityListArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(filteredArticleList[position])
    }

    override fun getItemCount(): Int {
        return filteredArticleList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                filteredArticleList = if (query.isEmpty()) {
                    articleList
                } else {
                    articleList.filter { it.title?.lowercase()?.contains(query) == true }
                }
                return FilterResults().apply { values = filteredArticleList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredArticleList = results?.values as List<DataItem>
                notifyDataSetChanged()
            }
        }
    }

    inner class ArticleViewHolder(private val binding: ActivityListArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: DataItem) {
            binding.title.text = article.title
            Glide.with(binding.root.context)
                .load(article.imageUrl)
                .into(binding.image)
            binding.content.text = article.content
            binding.author.text = article.category
            binding.publishedAt.text = formatDate(article.createdAt)

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailArtikelActivity::class.java).apply {
                    putExtra("title", article.title)
                    putExtra("imageUrl", article.imageUrl)
                    putExtra("content", article.content)
                    putExtra("author", article.category)
                    putExtra("createdAt", article.createdAt)
                }
                context.startActivity(intent)
            }
        }

        private fun formatDate(dateString: String?): String {
            if (dateString.isNullOrEmpty()) {
                return ""
            }

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

            val date = inputFormat.parse(dateString)
            return if (date != null) {
                outputFormat.format(date)
            } else {
                ""
            }
        }

    }

}
