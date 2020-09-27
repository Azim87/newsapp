package kg.azimus.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kg.azimus.newsapp.R
import kotlinx.android.synthetic.main.activity_news_details.*

private const val TAG = "NewsDetailsActivity"

class NewsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        getNewsDetails()
    }

    private fun getNewsDetails() {
        val author = intent.getStringExtra("author")
        val source = intent.getStringExtra("source")
        val description = intent.getStringExtra("description")
        val publishedAt = intent.getStringExtra("publishedAt")
        val title = intent.getStringExtra("title")
        val urlToImage = intent.getStringExtra("urlToImage")

        Log.d(TAG, "getNewsDetails:" + description)

        upDateUI(author, source, description, publishedAt, title, urlToImage)
    }

    private fun upDateUI(
        author: String?,
        source: String?,
        description: String?,
        publishedAt: String?,
        title: String?,
        urlToImage: String?
    ) {
        news_category.text = "source: ${source.toString()}"
        Glide.with(news_image.context).load(urlToImage).into(news_image)
        news_title.text = title
        news_desc.text = description
        news_publishedAt.text = " published: $publishedAt"
        news_author.text = "Author: $author"
    }
}