package andres.rangel.newsapp.adapters

import andres.rangel.newsapp.R
import andres.rangel.newsapp.databinding.ItemArticlePreviewBinding
import andres.rangel.newsapp.models.Article
import andres.rangel.newsapp.utils.Constants.Companion.IMAGE_NO_AVAILABLE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val binding = ItemArticlePreviewBinding.bind(holder.itemView)
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(binding.ivArticleImage)

            if(article.urlToImage == null)
                Glide.with(this).load(IMAGE_NO_AVAILABLE).into(binding.ivArticleImage)

            binding.apply {
                tvSource.text = article.source.name
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
            }

            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}