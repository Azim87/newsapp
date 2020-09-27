package kg.azimus.newsapp.data.remote

import kg.azimus.newsapp.model.News
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything/")
    fun getNews(@Query("q") sources: String): Deferred<News>
}

