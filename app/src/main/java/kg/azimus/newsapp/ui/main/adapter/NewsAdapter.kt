package kg.azimus.newsapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.softrunapps.paginatedrecyclerview.PaginatedAdapter
import kg.azimus.newsapp.R
import kg.azimus.newsapp.model.Articles


class NewsAdapter(
    private val function: (Articles) -> Unit
) : PaginatedAdapter<Articles, NewsAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
         val rootView: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(rootView, function)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    class NewsViewHolder(
        itemView: View,
        val function: (Articles) -> Unit
    ) : ViewHolder(itemView) {
        val titleText = itemView.findViewById<TextView>(R.id.item_title)
        val descriptionText = itemView.findViewById<TextView>(R.id.item_description)
        val imageView = itemView.findViewById<ImageView>(R.id.item_image)

        fun bind(news: Articles) {
            titleText.text = news.title
            descriptionText.text = news.description
            Glide.with(imageView.context).load(news.urlToImage).into(imageView)

            itemView.setOnClickListener {
                function(news)
            }
        }
    }
}