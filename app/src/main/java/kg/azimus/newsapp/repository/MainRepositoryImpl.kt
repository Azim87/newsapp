package kg.azimus.newsapp.repository

import kg.azimus.newsapp.util.UseCaseResult
import kg.azimus.newsapp.data.local.dao.NewsDao
import kg.azimus.newsapp.data.remote.NewsApiBuilder
import kg.azimus.newsapp.model.News

class MainRepositoryImpl(
    private val mNewsApi: NewsApiBuilder,
    private val mNewsDao: NewsDao
) {
    var newsResponse: News? = null
     suspend fun getGoogleNews(): UseCaseResult<News> {
        return try {
            newsResponse = mNewsApi.buildRetrofit()
                .getNews("tesla")
                .await()
            UseCaseResult.Success(newsResponse!!)

        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }

    fun insertNewsToLocalData(news: News) {
        mNewsDao.insertAll(news)
    }

    suspend fun readFromDataBase(): News {
        return mNewsDao.getAllNews()
    }
}